package dev.nice_code.api_key_proxy.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

/**
 * Configures the Firebase and SecretManager beans.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class CloudPlatformConfig {

  private final ApiKeyProxyProperties apiKeyProxyProperties;

  /**
   * Initializes Firebase and returns the instance.
   *
   * @return firebase instance
   */
  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public FirebaseApp firebase() {
    try {
      FirebaseOptions options =
          FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.getApplicationDefault())
              .setProjectId(apiKeyProxyProperties.getAppId())
              .build();
      return FirebaseApp.initializeApp(options);
    } catch (IOException e) {
      log.error("Error initializing Firebase. Failed with message: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Initializes the GCP SecretManagerClient to access SecretManager secrets.
   *
   * @return instance SecretManagerClient
   */
  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public SecretManagerServiceClient secretManager() throws IOException {
    return SecretManagerServiceClient.create();
  }
}
