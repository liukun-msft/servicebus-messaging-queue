package com.azure.spring.demo;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

	/**
	 * Message handler for the ServiceBusListener annotation, which will be finally set as {@link ServiceBusProcessorClient#processMessage() processMessage}.
	 * The destination parameter decides which Service Bus entity to listen to, should be modified as your expected queue name.
	 * The concurrency parameter sets the concurrency property of {@link ServiceBusProcessorClient}.
	 * @param message
	 */
	@ServiceBusListener(destination = "SERVICE_BUS_QUEUE_NAME", concurrency = "50")
	public void handleMessageFromServiceBus(String message) {
		log.info("Consume message: '{}'", message);
	}

}
