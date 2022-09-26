package com.azure.spring.demo;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ConsumerService {

	/**
	 * Message handler for the ServiceBusListener annotation, which will be finally set as {@link ServiceBusProcessorClient#processMessage() processMessage}.
	 * The destination parameter decides which Service Bus entity to listen to, should be modified as your expected queue name.
	 * The concurrency parameter sets the concurrency property of {@link ServiceBusProcessorClient}.
	 * @param message
	 */
	@ServiceBusListener(destination = "stress-test-queue1", concurrency = "10")
	public void handleMessageFromServiceBus1(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue2", concurrency = "10")
	public void handleMessageFromServiceBus2(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue3", concurrency = "10")
	public void handleMessageFromServiceBus3(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue4", concurrency = "10")
	public void handleMessageFromServiceBus4(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue5", concurrency = "10")
	public void handleMessageFromServiceBus5(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue6", concurrency = "10")
	public void handleMessageFromServiceBus6(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue7", concurrency = "10")
	public void handleMessageFromServiceBus7(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue8", concurrency = "10")
	public void handleMessageFromServiceBus8(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue9", concurrency = "10")
	public void handleMessageFromServiceBus9(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue10", concurrency = "10")
	public void handleMessageFromServiceBus10(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue11", concurrency = "10")
	public void handleMessageFromServiceBus11(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue12", concurrency = "10")
	public void handleMessageFromServiceBus12(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue13", concurrency = "10")
	public void handleMessageFromServiceBus13(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue14", concurrency = "10")
	public void handleMessageFromServiceBus14(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue15", concurrency = "10")
	public void handleMessageFromServiceBus15(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue16", concurrency = "10")
	public void handleMessageFromServiceBus16(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue17", concurrency = "10")
	public void handleMessageFromServiceBus17(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue18", concurrency = "10")
	public void handleMessageFromServiceBus18(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue19", concurrency = "10")
	public void handleMessageFromServiceBus19(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}
	@ServiceBusListener(destination = "stress-test-queue20", concurrency = "10")
	public void handleMessageFromServiceBus20(String message) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		log.info("Consume message: '{}'", message);
	}








}
