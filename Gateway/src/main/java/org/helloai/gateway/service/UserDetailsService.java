package org.helloai.gateway.service;

import org.helloai.gateway.entity.Users;
import org.helloai.gateway.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsService implements ReactiveUserDetailsService {
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  UsersRepository usersRepository;
  @Override
  public Mono<UserDetails> findByUsername(String username) {
    Users user =usersRepository.findByEmail(username);
    UserDetails userDetails = User.withUsername(user.getEmail())
        .password(passwordEncoder.encode(user.getPassword())).roles("admin").build();
    Mono<UserDetails> just = Mono.just(userDetails);
    return just;
  }
}
