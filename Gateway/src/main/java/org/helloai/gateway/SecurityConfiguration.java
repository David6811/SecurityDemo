package org.helloai.gateway;

import org.helloai.gateway.handler.AuthenticationFaillHandler;
import org.helloai.gateway.handler.AuthenticationSuccessHandler;
import org.helloai.gateway.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  private AuthenticationFaillHandler authenticationFaillHandler;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  public static final String HEADER_PREFIX = "Bearer ";

  private static final String[] excludedAuthPages = {
      "/test/excludedAuthPages",
      "/swagger-ui/**",
      "/v2/api-docs/**",
      "/swagger-resources/**"
  };

  @Bean
  SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf(csrf -> csrf.disable())
        .authorizeExchange()
        .pathMatchers(excludedAuthPages).permitAll()
        .and()
        .authorizeExchange()
        .pathMatchers("/test/admin").hasRole("admin")
        .pathMatchers("/**").authenticated()
//        .and()
//        .httpBasic()
        .and()
        .formLogin()
        .authenticationSuccessHandler(authenticationSuccessHandler)
        .authenticationFailureHandler(authenticationFaillHandler)
        .and()
        .logout().disable();
    return http.build();
  }

  //@Bean
  public WebFilter customFilter() {
    return (ServerWebExchange exchange, WebFilterChain chain) -> {
      String token = resolveToken(exchange.getRequest());
      if (jwtTokenUtil.validateAccessToken(token)) {
        return chain.filter(exchange);
      }
      return null;
    };
  }

  private String resolveToken(ServerHttpRequest request) {
    String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(8);
  }
}
