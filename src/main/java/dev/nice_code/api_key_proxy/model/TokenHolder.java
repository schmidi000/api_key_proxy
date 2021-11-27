package dev.nice_code.api_key_proxy.model;

import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Used as credentials of SecurityContext.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Data
@AllArgsConstructor
public class TokenHolder {
  private FirebaseToken decodedToken;
  private String idToken;
}
