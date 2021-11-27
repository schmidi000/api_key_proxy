package dev.nice_code.api_key_proxy.exception;

/**
 * Exception that is thrown when there is a problem with a third party API.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
public class ThirdPartyApiException extends RuntimeException {
  public ThirdPartyApiException(String msg) {
    super(String.format("Problem with third party API: %s", msg));
  }
}
