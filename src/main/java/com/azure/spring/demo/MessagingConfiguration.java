package com.azure.spring.demo;

import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {

    private static final Logger LOGGER  = LoggerFactory.getLogger(MessagingConfiguration.class);

    @ServiceBusListener(destination = "queue-001", concurrency = "10")
    public void handleMessageFromServiceBus(String message) {
        LOGGER.info("New Service Bus queue message received: '{}'", message);
    }

//    @Bean
//    PropertiesSupplier<ConsumerIdentifier, ProcessorProperties> supplyTest(){
//        return key -> {
//            ProcessorProperties processorProperties = new ProcessorProperties();
//            processorProperties.setMaxConcurrentCalls(10);
//            return processorProperties;
//        };
//    }
}
