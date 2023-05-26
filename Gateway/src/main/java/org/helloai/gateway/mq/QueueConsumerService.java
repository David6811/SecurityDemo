package org.helloai.gateway.mq;

import javax.jms.JMSException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


@Component
public class QueueConsumerService {
    @JmsListener(destination = "${myqueue}")
    public void receive(String msg) throws JMSException, MessagingException {
        System.out.println("receive message: "+ msg);
    }
}


