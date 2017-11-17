package org.synyx.coffeecontrol.make.web.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.springframework.stereotype.Component;

import java.nio.charset.Charset;


@Component
public class CommandSender {

    public void sendCommand(String command) {

        final MqttClient mqttClient = mqttConnect();

        MqttMessage message = new MqttMessage(command.getBytes(Charset.forName("UTF-8")));

        mqttClient.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable throwable) {
                }


                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                }
            });

        try {
            // mqttClient.publish("coffee/command/a020a600f704", message);
            mqttClient.publish("coffee/command/joo", message);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


    private MqttClient mqttConnect() {

        final MemoryPersistence memoryPersistence = new MemoryPersistence();
        final MqttClient mqttClient;

        try {
            mqttClient = new MqttClient("tcp://iot-central.synyx.coffee:1883", "coffee-recipe", memoryPersistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            mqttClient.connect(connOpts);

            return mqttClient;
        } catch (MqttException e) {
            throw new RuntimeException("exception connecting to mqtt ", e);
        }
    }
}
