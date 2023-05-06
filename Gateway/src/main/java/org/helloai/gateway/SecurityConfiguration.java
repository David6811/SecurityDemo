package org.helloai.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Bean
  SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf(csrf -> csrf.disable())
        .authorizeExchange()
        .pathMatchers("/**").authenticated()
        .and()
        .httpBasic();
    return http.build();
  }
}
