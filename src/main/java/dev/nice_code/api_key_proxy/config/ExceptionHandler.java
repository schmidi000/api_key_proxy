package dev.nice_code.api_key_proxy.config;

import dev.nice_code.api_key_proxy.exception.AuthenticationException;
import dev.nice_code.api_key_proxy.exception.ThirdPartyApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for some exceptions.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String bodyOfResponse =
        "JSON object validation failed. Seems like someone is playing around ;)";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
