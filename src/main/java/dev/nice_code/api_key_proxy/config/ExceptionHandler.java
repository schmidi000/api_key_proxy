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

import javax.validation.ConstraintViolationException;

/**
 * Global exception handler for some exceptions.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(
      value = {IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Oops, something happened.";
    ex.printStackTrace();
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

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

  @org.springframework.web.bind.annotation.ExceptionHandler(value = {ThirdPartyApiException.class})
  protected ResponseEntity<Object> handleExternalApiProblem(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "There is a problem with an external API.";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(value = {AuthenticationException.class})
  protected ResponseEntity<Object> handleAuthenticationProblem(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Authentication required.";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(
      value = {ConstraintViolationException.class})
  protected ResponseEntity<Object> handleValidationException(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse =
        "JSON object validation failed. Seems like someone is playing around ;)";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
