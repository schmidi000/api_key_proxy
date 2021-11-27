package dev.nice_code.api_key_proxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nice_code.api_key_proxy.filter.FirebaseTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Security configuration.
 *
 * <p>This security configuration should be used in the production environment, because the
 * filtering of requests is enabled.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Profile("prod")
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfigProd extends WebSecurityConfigurerAdapter {

  public final FirebaseTokenFilter tokenAuthenticationFilter;
  private final ObjectMapper objectMapper;

  @Bean
  public AuthenticationEntryPoint restAuthenticationEntryPoint() {
    return (httpServletRequest, httpServletResponse, e) -> {
      Map<String, Object> errorObject = new HashMap<>();
      errorObject.put("message", "You are not welcome here. GTFO!");
      errorObject.put("error", HttpStatus.UNAUTHORIZED);
      errorObject.put("code", 401);
      errorObject.put("timestamp", new Timestamp(new Date().getTime()));
      httpServletResponse.setContentType("application/json;charset=UTF-8");
      httpServletResponse.setStatus(401);
      httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
    };
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedMethods(Arrays.asList("GET", "OPTIONS"));
    configuration.setAllowedHeaders(
        Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "Accept-Encoding",
            "Content-Length",
            "Host",
            "User-Agent",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Headers",
            "Access-Control-Request-Method",
            "X-Requested-With",
            "X-Auth-Token",
            "X-Xsrf-Token",
            "Cache-Control",
            "Id-Token"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.cors()
        .configurationSource(corsConfigurationSource())
        .and()
        .csrf()
        .disable()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint())
        .and()
        .authorizeRequests()
        .antMatchers("/_ah/**", "/favicon.ico")
        .permitAll()
        .antMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
}
