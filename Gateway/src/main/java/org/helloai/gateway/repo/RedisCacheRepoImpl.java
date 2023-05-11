package org.helloai.gateway.repo;

import java.time.Duration;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheRepoImpl {

  @Autowired private RedisTemplate<String, String> redisTemplate;

  private static RedisCacheRepoImpl redisUtil;

  @PostConstruct
  public void init() {
    redisUtil = this;
    redisUtil.redisTemplate = this.redisTemplate;
  }

  public void set(String key,String value, Duration duration) {
    redisUtil
        .redisTemplate
        .opsForValue()
        .set(key,value,duration);
  }


  @Nullable
  public String get(String key) {
    return redisUtil.redisTemplate.opsForValue().get(key);
  }

  public void delete(String key) {
    redisUtil.redisTemplate.delete(key);
  }
}
