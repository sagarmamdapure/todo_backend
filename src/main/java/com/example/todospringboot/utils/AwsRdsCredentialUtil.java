package com.example.todospringboot.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

@Component
public class AwsRdsCredentialUtil {

  public static HashMap<String, String> getSecret(String secretName, String region) {

    AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(region).build();
    GetSecretValueRequest getSecretValueRequest =
            new GetSecretValueRequest().withSecretId(secretName);
    GetSecretValueResult getSecretValueResult;

    getSecretValueResult = client.getSecretValue(getSecretValueRequest);
    String secret;
    if (getSecretValueResult.getSecretString() != null) {
      secret = getSecretValueResult.getSecretString();
    } else {
      secret =
              new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
    }
    Gson gson = new Gson();
    return gson.fromJson(secret, new TypeToken<HashMap<String, String>>() {
    }.getType());
  }

  public SnsClient getSnsClient() {
    return SnsClient.builder()
            .credentialsProvider(
                    getAwsCredentials("AKIAIKBHU2JYCQZ5JNVQ", "c1DOnis0R1e4v04AR/rArjSHuncXtTIauWaMAPXl"))
            // TODO : hide aws credentials
            .region(Region.US_EAST_1) // Set your selected region
            .build();
  }

  private AwsCredentialsProvider getAwsCredentials(String accessKeyID, String secretAccessKey) {
    AwsBasicCredentials awsBasicCredentials =
            AwsBasicCredentials.create(accessKeyID, secretAccessKey);
    return () -> awsBasicCredentials;
  }

  public String getRandomNumber() {
    Random rnd = new Random();
    int number = rnd.nextInt(999999);
    return String.format("%06d", number);
  }
}
