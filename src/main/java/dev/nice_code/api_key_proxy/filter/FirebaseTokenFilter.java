package dev.nice_code.api_key_proxy.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import dev.nice_code.api_key_proxy.exception.AuthenticationException;
import dev.nice_code.api_key_proxy.model.TokenHolder;
import dev.nice_code.api_key_proxy.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Requires users to send a valid Firebase Auth token in the Bearer HTTP header.
 *
 * <p>Any request without a valid token will be discarded.
 *
 * <p>This filter is only executed when running the "prod" Spring profile.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Profile("prod")
@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseTokenFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    verifyToken(request);
    filterChain.doFilter(request, response);
  }

  /**
   * Get the Bearer token (Firebase ID token) from the HTTP request.
   *
   * @param request HTTP request
   * @return token, or null if no token was sent
   */
  public String getBearerToken(HttpServletRequest request) {
    String bearerToken = null;
    String authorization = request.getHeader("Authorization");
    if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
      bearerToken = authorization.substring(7);
    }
    return bearerToken;
  }

  /**
   * Verifies the Firebase token sent by the client and checks if the user's email is verified.
   *
   * @param request HTTP request
   * @throws AuthenticationException if the token is not valid, or the user's email is not verified
   */
  private void verifyToken(HttpServletRequest request) {
    String token = getBearerToken(request);

    try {
      if (token != null && !token.equalsIgnoreCase("undefined")) {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        User user = firebaseTokenToUserDto(decodedToken);

        if (user != null && user.isEmailVerified()) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  user, new TokenHolder(decodedToken, token), null);

          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (FirebaseAuthException e) {
      e.printStackTrace();
      throw new AuthenticationException("Could not verify token.");
    }
  }

  private User firebaseTokenToUserDto(FirebaseToken decodedToken) {
    if (decodedToken == null) return null;
    return User.builder()
        .uid(decodedToken.getUid())
        .name(decodedToken.getName())
        .email(decodedToken.getEmail())
        .isEmailVerified(decodedToken.isEmailVerified())
        .build();
  }
}
