package com.azure.spring.demo;

import com.azure.spring.messaging.servicebus.core.ServiceBusTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);

    private static final String QUEUE_NAME = "queue-001";

    @Autowired
    private ServiceBusTemplate template;

    @PostMapping("/messages")
    public String postMessage(@RequestParam String message) {

        LOGGER.info("Sending message");

        template.sendAsync(QUEUE_NAME, MessageBuilder.withPayload(message).build()).block();

        return message;
    }
}
