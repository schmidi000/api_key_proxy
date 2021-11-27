package dev.nice_code.api_key_proxy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security config for local testing.
 *
 * <p>Permits every request.
 *
 * @author Tobias Américo-Schmidradler (https://nice-code.dev)
 */
@Profile("!prod")
@Configuration
@EnableWebSecurity
public class SecurityConfigLocal extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
  }
}
