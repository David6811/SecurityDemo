package org.helloai.gateway.mq;

import javax.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class ConfigBean {
  @Value("${myqueue}")
  private String myQueue;

  @Bean
  public Queue queue() {
    return new ActiveMQQueue(myQueue);
  }
}
