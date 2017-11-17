package org.synyx.coffeecontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CoffeecontrolApplication {

    public static void main(String[] args) {

        SpringApplication.run(CoffeecontrolApplication.class, args);
    }

/*
    @Bean
    public MessageChannel mqttInputChannel() {

        return new DirectChannel();
    }


    @Bean
    public MessageProducer inbound() {

        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883",
                "testClient", "topic1", "topic2");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());

        return adapter;
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {

        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {

                System.out.println(message.getPayload());
            }
        };
    }
    */
}
