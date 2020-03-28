package com.example.todospringboot.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Base64;
import java.util.HashMap;

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
}
