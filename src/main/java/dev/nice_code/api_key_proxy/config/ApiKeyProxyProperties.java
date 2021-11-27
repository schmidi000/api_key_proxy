package dev.nice_code.api_key_proxy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * GCP and Firebase config values loaded from application.yaml.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Component
@ConfigurationProperties(prefix = "proxy")
@Getter
@Setter
public class ApiKeyProxyProperties {
  private final Secrets secrets = new Secrets();
  private String appId;

  @Getter
  @Setter
  public static class Secrets {
    private String googlePlacesApiKeySecretVersion;
    private String googlePlacesApiKeySecretName;
    private String projectId;
  }
}
