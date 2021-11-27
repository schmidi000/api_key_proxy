package dev.nice_code.api_key_proxy.service;

import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.RankBy;
import dev.nice_code.api_key_proxy.exception.ThirdPartyApiException;
import dev.nice_code.api_key_proxy.util.StackTraceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This service executes the actual Google Places calls.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlacesService {

  private final GeoApiContext geoApiContext;

  /**
   * Returns all supermarkets that are within a given radius around a specified geographical
   * coordinate.
   *
   * <p>This method calls the https://maps.googleapis.com/maps/api/place/nearbysearch endpoint of
   * the Google Maps API.
   *
   * @param latitude geographical latitude
   * @param longitude geographical longitude
   * @param languageCode the language you want the results in (en, de, pt etc.)
   * @return JSON formatted string of all nearby places, ordered by distance
   * @throws ThirdPartyApiException when a problem calling Google Places occurs
   */
  public String getNearbySupermarkets(double latitude, double longitude, String languageCode)
      throws ThirdPartyApiException {
    try {
      PlacesSearchResponse nearbySearchResult =
          PlacesApi.nearbySearchQuery(geoApiContext, new LatLng(latitude, longitude))
              .type(PlaceType.SUPERMARKET)
              .language(languageCode)
              .rankby(RankBy.DISTANCE)
              .await();

      return new GsonBuilder().setPrettyPrinting().create().toJson(nearbySearchResult);
    } catch (ApiException | InterruptedException | IOException e) {
      log.error(
          "Error getting nearby places from Places API. Failed with message: {}. Stack trace: {}",
          e.getMessage(),
          StackTraceUtil.stackTraceToString(e));
      throw new ThirdPartyApiException("Error getting nearby stores.");
    }
  }
}
