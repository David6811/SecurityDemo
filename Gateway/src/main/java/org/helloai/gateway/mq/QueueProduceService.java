package org.helloai.gateway.mq;

import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class QueueProduceService {
  @Autowired private JmsMessagingTemplate jmsMessagingTemplate;
  @Autowired private Queue queue;

  public void produceMsg(String msg) {
    jmsMessagingTemplate.convertAndSend(queue, msg);
  }
}
