package org.helloai.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication()
@EnableEurekaClient
public class GatewayApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayApp.class, args);
    }
}
