package dev.nice_code.api_key_proxy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller implements the endpoints /_ah/start and /_ah/stop required by AppEngine.
 *
 * <p>They are empty and just in here for the sake of completeness.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@RestController
@RequestMapping("_ah")
public class AppEngineController {
  @GetMapping("start")
  public ResponseEntity<Void> init() {
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("stop")
  public ResponseEntity<Void> stop() {
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
