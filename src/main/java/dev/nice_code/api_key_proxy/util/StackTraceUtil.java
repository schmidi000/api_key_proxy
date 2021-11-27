package dev.nice_code.api_key_proxy.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Converts every Exception's stack trace into a string. This makes debugging on Google AppEngine
 * easier.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
public class StackTraceUtil {
  /**
   * Returns the stack trace of the exception as a string.
   *
   * @param ex exception the get the stack trace from
   * @return string representation of the stack trace
   */
  public static String stackTraceToString(Exception ex) {
    StringWriter errors = new StringWriter();
    ex.printStackTrace(new PrintWriter(errors));
    return errors.toString();
  }
}
