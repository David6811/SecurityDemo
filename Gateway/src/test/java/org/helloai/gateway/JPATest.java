package org.helloai.gateway;

import static com.google.common.truth.Truth.assertThat;

import org.helloai.gateway.entity.Users;
import org.helloai.gateway.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = GatewayApp.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class JPATest {
  @Autowired
  private UsersRepository usersRepository;
  private static final String password = "123456";

    @Test
    public void case_0_JPA_whenCorrectEmailProvided_shouldReturnCorrectPassword()  {
      Users users=usersRepository.findByEmail("test@helloai.ink");
      assertThat(password).isEqualTo(users.getPassword());
    }
  }
