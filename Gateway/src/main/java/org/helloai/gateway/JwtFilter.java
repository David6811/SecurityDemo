package org.helloai.gateway;

import java.util.stream.Stream;
import org.helloai.gateway.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class JwtFilter implements WebFilter {
  @Autowired
  JwtTokenUtil jwtTokenUtil;

  private static final String[] excludedAuthPages = {
      "/test/excludedAuthPages",
      "/swagger-ui/**",
      "/v2/api-docs/**",
      "/swagger-resources/**"
  };

  public static final String HEADER_PREFIX = "Bearer ";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String url = request.getPath().value();
    System.out.println(url);
    if(Stream.of("/swagger-ui", "/v2/api-docs", "/swagger-resources","/test/testActiveMQ").anyMatch(url::contains)){
      return chain.filter(exchange);
    }

    String token = resolveToken(exchange.getRequest());
    if (jwtTokenUtil.validateAccessToken(token)) {
      return chain.filter(exchange);
    }
    return null;
  }

  private String resolveToken(ServerHttpRequest request) {
    String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }


}




