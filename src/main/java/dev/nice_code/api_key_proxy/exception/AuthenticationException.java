package dev.nice_code.api_key_proxy.exception;

/**
 * Exception that is thrown when there is an authentication problem.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
public class AuthenticationException extends RuntimeException {
  public AuthenticationException(String msg) {
    super(String.format("Authentication failed: %s", msg));
  }
}
