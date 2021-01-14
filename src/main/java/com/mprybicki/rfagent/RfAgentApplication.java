package com.mprybicki.rfagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RfAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfAgentApplication.class, args);
    }

}
