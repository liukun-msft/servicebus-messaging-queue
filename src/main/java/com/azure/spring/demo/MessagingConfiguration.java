package com.azure.spring.demo;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.azure.spring.cloud.core.customizer.AzureServiceClientBuilderCustomizer;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusErrorHandler;
import com.azure.spring.messaging.ConsumerIdentifier;
import com.azure.spring.messaging.PropertiesSupplier;
import com.azure.spring.messaging.implementation.config.AzureListenerEndpointRegistry;
import com.azure.spring.messaging.implementation.listener.MessageListenerContainerFactory;
import com.azure.spring.messaging.listener.MessageListenerContainer;
import com.azure.spring.messaging.servicebus.core.DefaultServiceBusNamespaceProcessorFactory;
import com.azure.spring.messaging.servicebus.core.ServiceBusProcessorFactory;
import com.azure.spring.messaging.servicebus.core.properties.NamespaceProperties;
import com.azure.spring.messaging.servicebus.core.properties.ProcessorProperties;
import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;
import com.azure.spring.messaging.servicebus.implementation.core.config.ServiceBusMessageListenerContainerFactory;
import com.azure.spring.messaging.servicebus.support.converter.ServiceBusMessageConverter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionException;

@Slf4j
@Configuration
public class MessagingConfiguration {

    private static ExecutorService single = Executors.newSingleThreadExecutor();

    /**
     * Provide customization for the underlying {@link ServiceBusProcessorClient}. This bean modifies properties of prefetch, auto-complete
     * and max-auto-lock-renew-duration.
     * @return a {@link PropertiesSupplier}
     */
    @Bean
    PropertiesSupplier<ConsumerIdentifier, ProcessorProperties> processorPropertiesPropertiesSupplier() {
        return key -> {
            ProcessorProperties processorProperties = new ProcessorProperties();
            processorProperties.setPrefetchCount(80);
            processorProperties.setAutoComplete(false);
            processorProperties.setMaxAutoLockRenewDuration(Duration.ofSeconds(0));
            return processorProperties;
        };
    }

    /**
     * The error hander will be finally set to {@link ServiceBusProcessorClient#processError() processError}.
     * @param endpointRegistry
     * @return
     */
    @Bean
    ServiceBusErrorHandler errorHandler(AzureListenerEndpointRegistry endpointRegistry) {
        return errorContext -> {
            log.error("Error occurred on entity {}. Error: {}",
                    errorContext.getEntityPath(),
                    errorContext.getException());
            // restart the container to mitigate ....
            // endpointRegistry.getListenerContainer("<container-id").stop();// replace container-id to real ID set in
            // the listener annotation
            // endpointRegistry.getListenerContainer("<container-id").start();
        };
    }

    /**
     *
     * Override the default MessageListenerContainerFactory, adding the setErrorHandler in the code
     *
     * @param serviceBusProcessorFactory
     * @param serviceBusErrorHandler
     * @return
     */
    @Bean(name = "azureServiceBusListenerContainerFactory")
    @ConditionalOnMissingBean(name = "azureServiceBusListenerContainerFactory")
    MessageListenerContainerFactory<? extends MessageListenerContainer> azureServiceBusListenerContainerFactory(
            ServiceBusProcessorFactory serviceBusProcessorFactory, ServiceBusErrorHandler serviceBusErrorHandler,
            ServiceBusMessageConverter serviceBusMessageConverter) {
        ServiceBusMessageListenerContainerFactory factory =
                new ServiceBusMessageListenerContainerFactory(serviceBusProcessorFactory);
        factory.setErrorHandler(serviceBusErrorHandler);
        factory.setMessageConverter(serviceBusMessageConverter);
        return factory;
    }

    @Bean
    ServiceBusProcessorFactory defaultServiceBusNamespaceProcessorFactory(
            NamespaceProperties properties, ObjectProvider<PropertiesSupplier<ConsumerIdentifier,
            ProcessorProperties>> suppliers) {
        DefaultServiceBusNamespaceProcessorFactory f =
                new DefaultServiceBusNamespaceProcessorFactory(properties, suppliers.getIfAvailable());
        f.addBuilderCustomizer(customizerClientBuilder());
        return f;
    }

    /**
     * Override the {@link ServiceBusProcessorClient#processMessage() processMessage}, which will abandon a message if it will be lock-expired in 5s, otherwise
     * call the above {@link ServiceBusListener} handler to consume messages and then complete after messages getting consumed.
     * @return
     */
    private AzureServiceClientBuilderCustomizer<ServiceBusClientBuilder.ServiceBusProcessorClientBuilder> customizerClientBuilder() {
        return new AzureServiceClientBuilderCustomizer<ServiceBusClientBuilder.ServiceBusProcessorClientBuilder>() {

            @Override
            public void customize(ServiceBusClientBuilder.ServiceBusProcessorClientBuilder builder) {
                Consumer<ServiceBusReceivedMessageContext> consumer = getProcessMessage(builder);
                builder.processMessage(context -> {

                    // TODO: When the lock expires in 2 seconds, ignore this message.
                    if (context.getMessage().getLockedUntil().compareTo(OffsetDateTime.now().plusSeconds(5)) < 0) {
                        log.info("The message lock:{} will expired at {}, ignore message:{}",
                                context.getMessage().getLockToken(), context.getMessage().getLockedUntil(),
                                context.getMessage().getBody());
                        context.abandon();
                        return;
                    } else {
                        log.info("Start consumption message:{}, lock:{}, expire:{}", context.getMessage().getBody(),
                                context.getMessage().getLockToken(), context.getMessage().getLockedUntil());
                    }

                    try {
                        consumer.accept(context);
                        context.complete();
                    } catch (Exception e) {
                        if (e instanceof ConversionException) {
                            log.warn("Convert message failure, message:{}", context.getMessage().getBody());
                            context.complete();
                        }
                        log.error("Consume messsage failure!", e);
                    }
                });
            }
        };
    }

    @SuppressWarnings("unchecked")
    private Consumer<ServiceBusReceivedMessageContext> getProcessMessage(ServiceBusClientBuilder.ServiceBusProcessorClientBuilder builder) {
        Consumer<ServiceBusReceivedMessageContext> consumer = null;
        try {
            Field processMessageField = builder.getClass().getDeclaredField("processMessage");
            processMessageField.setAccessible(true);
            consumer = (Consumer<ServiceBusReceivedMessageContext>)processMessageField.get(builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return consumer;
    }


}
