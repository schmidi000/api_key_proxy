package dev.nice_code.api_key_proxy.controller;

import dev.nice_code.api_key_proxy.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller proxies all geo-request related queries to Google Maps and Google Places.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("geo")
public class PlacesController {
  private final PlacesService placesService;

  @GetMapping("/nearbySupermarkets")
  public ResponseEntity<String> getNearbySupermarkets(
      @RequestParam("latitude") double latitude,
      @RequestParam("longitude") double longitude,
      @RequestParam("language") String language) {
    return ResponseEntity.ok(placesService.getNearbySupermarkets(latitude, longitude, language));
  }
}
