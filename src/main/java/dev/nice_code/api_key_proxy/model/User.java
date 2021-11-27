package dev.nice_code.api_key_proxy.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Used as principals of SecurityContext.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Data
@Builder
public class User implements Serializable {
  private String uid;
  private String name;
  private String email;
  private boolean isEmailVerified;
}
