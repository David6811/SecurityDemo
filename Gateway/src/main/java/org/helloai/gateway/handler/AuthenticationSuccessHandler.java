package org.helloai.gateway.handler;


import io.netty.util.CharsetUtil;
import org.helloai.gateway.entity.Users;
import org.helloai.gateway.jwt.JwtTokenUtil;
import org.helloai.gateway.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
  @Autowired
  UsersRepository usersRepository;
  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @Override
  public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
    ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
    response.setStatusCode(HttpStatus.OK);
    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    //Todo more details or token provided
    Users user=usersRepository.findByEmail(username);
    String accessToken = jwtTokenUtil.generateAccessToken(user);

    DataBuffer buffer = response.bufferFactory().wrap(accessToken.getBytes(CharsetUtil.UTF_8));
    return response.writeWith(Mono.just(buffer));
  }
}
