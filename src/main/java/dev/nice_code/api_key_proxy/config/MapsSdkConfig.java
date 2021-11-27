package dev.nice_code.api_key_proxy.config;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import com.google.maps.GeoApiContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

/**
 * Configures the GeoApiConfig bean for Google Maps API requests.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class MapsSdkConfig {

  private final ApiKeyProxyProperties apiKeyProxyProperties;
  private final SecretManagerServiceClient secretManagerServiceClient;

  /**
   * Returns an instance of GeoApiContext used for Google Maps SDK requests.
   *
   * @return instance of GeoApiContext
   */
  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  @DependsOn("secretManager")
  public GeoApiContext getGeoApiContext() {
    String projectId = apiKeyProxyProperties.getSecrets().getProjectId();
    String secretKey = apiKeyProxyProperties.getSecrets().getGooglePlacesApiKeySecretName();
    String secretVersion = apiKeyProxyProperties.getSecrets().getGooglePlacesApiKeySecretVersion();

    SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretKey, secretVersion);
    AccessSecretVersionResponse response =
        secretManagerServiceClient.accessSecretVersion(secretVersionName);
    String googlePlacesApiKey = response.getPayload().getData().toStringUtf8();

    return new GeoApiContext.Builder().apiKey(googlePlacesApiKey).build();
  }
}
