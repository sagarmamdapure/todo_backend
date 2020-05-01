package com.example.todospringboot.service;

import com.example.todospringboot.dao.UserRepositoryDao;
import com.example.todospringboot.entity.User;
import com.example.todospringboot.exceptions.ExpiredOTP;
import com.example.todospringboot.exceptions.InvalidOTP;
import com.example.todospringboot.models.ForgetUserPayload;
import com.example.todospringboot.utils.AwsRdsCredentialUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserRepositoryServiceImpl implements UserRepositoryService {

  final UserRepositoryDao userRepositoryDao;
  final AwsRdsCredentialUtil awsRdsCredentialUtil;
  final PasswordEncoder encoder;
  private Logger logger = Logger.getLogger(getClass().getName());

  public UserRepositoryServiceImpl(
          UserRepositoryDao userRepositoryDao,
          AwsRdsCredentialUtil awsRdsCredentialUtil,
          PasswordEncoder encoder) {
    this.userRepositoryDao = userRepositoryDao;
    this.awsRdsCredentialUtil = awsRdsCredentialUtil;
    this.encoder = encoder;
  }

  @Override
  @Transactional
  public Optional<User> findByUsername(String username) {
    return userRepositoryDao.findByUsername(username);
  }

  @Override
  @Transactional
  public Boolean existsByUsername(String username) {
    return userRepositoryDao.existsByUsername(username);
  }

  @Override
  @Transactional
  public Boolean existsByEmail(String email) {
    return userRepositoryDao.existsByEmail(email);
  }

  @Override
  @Transactional
  public void save(User user) {
    userRepositoryDao.save(user);
  }

  @Override
  @Transactional
  public void update(User user) {
    userRepositoryDao.update(user);
  }

  @Override
  @Transactional
  public String forgetPassword(ForgetUserPayload forgetUserPayload) throws URISyntaxException {
    SnsClient snsClient = this.awsRdsCredentialUtil.getSnsClient();
    String otp = this.awsRdsCredentialUtil.getRandomNumber();
    Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
    smsAttributes.put(
            "AWS.SNS.SMS.MaxPrice",
            MessageAttributeValue.builder().stringValue("0.50").dataType("Number").build());
    smsAttributes.put(
            "AWS.SNS.SMS.SMSType",
            MessageAttributeValue.builder().stringValue("Transactional").dataType("String").build());

    // TODO : fetch email from user table and send otp to over the mail
    String otpFormat =
            String.format(
                    "Use %s as your (OTP) for TODO Application. OTP is usable once & valid for 5 mins. Please do not share it with anyone",
                    otp);
    Optional<User> username =
            this.userRepositoryDao.findByUsername(forgetUserPayload.getUsername());
    if (username.isPresent()) {
      User user = username.get();
      PublishResponse publishResponse =
              snsClient.publish(
                      PublishRequest.builder()
                              .phoneNumber(user.getContact())
                              .message(otpFormat)
                              .messageAttributes(smsAttributes)
                              .build());
      if (publishResponse.sdkHttpResponse().isSuccessful()) {
        user.setOtp(otp);
        user.setOtpIssuedAt(new Timestamp(System.currentTimeMillis()));
        this.userRepositoryDao.update(user);
      } else {
        if (publishResponse.sdkHttpResponse().statusText().isPresent())
          throw new ResponseStatusException(
                  HttpStatus.INTERNAL_SERVER_ERROR,
                  publishResponse.sdkHttpResponse().statusText().get());
      }

      logger.info(String.format("Message publishing to phone: %s successful", user.getContact()));
    }
    snsClient.close();
    return "If username is present then One Time Password (OTP) sent to the registered phone/email";
  }

  @Override
  @Transactional
  public Boolean changePassword(ForgetUserPayload forgetUserPayload) {
    String receivedOtp = forgetUserPayload.getOtp();
    Optional<User> username =
            this.userRepositoryDao.findByUsername(forgetUserPayload.getUsername());
    if (username.isPresent()) {
      User user = username.get();
      try {
        if ((new Timestamp(System.currentTimeMillis()).getTime() - user.getOtpIssuedAt().getTime())
                > 300000) {
          // Checking if the otp issued time and current time difference is more than 5 mins
          throw new ExpiredOTP();
        } else if (!receivedOtp.equals(user.getOtp()) && !receivedOtp.isEmpty()) {
          throw new InvalidOTP();
        } else {
          user.setPassword(encoder.encode(forgetUserPayload.getPassword()));
          user.setOtp(null);
          user.setOtpIssuedAt(null);
          this.userRepositoryDao.update(user);
        }
      } catch (NullPointerException e) {
        throw new InvalidOTP();
      }
    }
    return true;
  }
}
