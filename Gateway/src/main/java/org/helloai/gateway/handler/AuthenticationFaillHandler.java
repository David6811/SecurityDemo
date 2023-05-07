package org.helloai.gateway.handler;

import io.netty.util.CharsetUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFaillHandler implements ServerAuthenticationFailureHandler {

  @Override
  public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
    ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
    response.setStatusCode(HttpStatus.BAD_REQUEST);
    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
    //Todo Encapsulate exception
    DataBuffer buffer = response.bufferFactory().wrap(exception.toString().getBytes(CharsetUtil.UTF_8));
    return response.writeWith(Mono.just(buffer));
  }
}