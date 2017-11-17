package org.synyx.coffeecontrol.make.web.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import org.synyx.coffeecontrol.Loggable;

import java.nio.charset.Charset;


@Component
public class CommandSender implements Loggable {

    @Value("${mqtt.broker.uri:tcp://iot-central.synyx.coffee:1883}")
    private String mqttBrokerUri;

    @Value("${mqtt.client.id:coffee-recipe}")
    private String mqttClientId;

    public void sendCommand(String command, String machineId) {

        final MqttClient mqttClient = mqttConnect();

        MqttMessage message = new MqttMessage(command.getBytes(Charset.forName("UTF-8")));

        mqttClient.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable throwable) {

                    logger().error("lost connection to mqtt broker");
                }


                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                    logger().info("message arrived at mqtt broker");
                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                    logger().info("message delivery complete");
                }
            });

        try {
            final String topic = String.format("coffee/command/%s", machineId);
            mqttClient.publish(topic, message);
            logger().info("Successfully delivered message {} to topic {}", message, topic);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


    private MqttClient mqttConnect() {

        final MemoryPersistence memoryPersistence = new MemoryPersistence();
        final MqttClient mqttClient;

        try {
            mqttClient = new MqttClient(mqttBrokerUri, mqttClientId, memoryPersistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            mqttClient.connect(connOpts);

            return mqttClient;
        } catch (MqttException e) {
            throw new RuntimeException("exception connecting to mqtt ", e);
        }
    }
}
