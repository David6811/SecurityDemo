package org.helloai.gateway;

import static com.google.common.truth.Truth.assertThat;
import java.time.Duration;
import org.helloai.gateway.repo.RedisCacheRepoImpl;
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
public class RedisTest {
  @Autowired
  private RedisCacheRepoImpl redisCacheRepo;
  private static final String key = "testKey";
  private static final String value = "testValue";

    @Test
    public void case_0_Redis_getValue_shouldReturnValueHasBeenSet()  {
      redisCacheRepo.set(key,value, Duration.ofHours(3));
      String retrieveValue =redisCacheRepo.get(key);
      assertThat(retrieveValue).isEqualTo(value);
    }
  }
