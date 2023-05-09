package org.helloai.gateway;

import org.helloai.gateway.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class JwtFilter implements GlobalFilter {
  @Autowired
  JwtTokenUtil jwtTokenUtil;

  public static final String HEADER_PREFIX = "Bearer ";
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
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




