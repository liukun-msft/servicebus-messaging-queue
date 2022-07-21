# How to run
The sample takes Service Bus queue as example.

1. Fill in the connection string property in [application.yaml](./src/main/resources/application.yml).
2. Replace the "SERVICE_BUS_QUEUE_NAME" with your actual queue name in [ConsumerService.java](./src/main/java/com/azure/spring/demo/ConsumerService.java).
3. Replace the "SERVICE_BUS_QUEUE_NAME" with the same value as above in [ProducerController.java](./src/main/java/com/azure/spring/demo/ProducerController.java).
4. Build and run with below command:
   ```shell
   mvn clean package
   mvn spring-boot:run
   ```
5. When the application getting running, you can use curl to send messages to your queue;
   ```shell
   curl -X POST http://localhost:8080/messages?message=hello
   ```
   You should see "hello" posted to your application's logs. For example:
   ```shell
   2022-07-21 16:20:17.880  INFO 23336 --- [oundedElastic-2] c.a.spring.demo.MessagingConfiguration   : Start consumption message:hello, lock:cbe39c19-9793-420e-9e1e-924de631ab23, expire:2022-07-21T08:20:47.744Z
   2022-07-21 16:20:17.887  INFO 23336 --- [oundedElastic-2] com.azure.spring.demo.ConsumerService    : Consume message: 'hello'
   ```