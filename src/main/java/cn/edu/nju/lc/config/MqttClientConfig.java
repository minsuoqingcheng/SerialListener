package cn.edu.nju.lc.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqttClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(MqttClientConfig.class);

    @Value("${spring.mqtt.broker}")
    private String broker;
    @Value("${spring.mqtt.clientId}")
    private String clientId;
    @Value("${spring.mqtt.username}")
    private String username;
    @Value("${spring.mqtt.password}")
    private String password;


    @Bean
    public MqttClient mqttClient() {
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
            // 创建链接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 在重新启动和重新连接时记住状态
            connOpts.setCleanSession(false);
            // 设置连接的用户名
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            // 建立连接
            mqttClient.connect(connOpts);
            return mqttClient;
        } catch (MqttException e) {
            logger.error("create mqttClient failed, exception is " + e.getMessage());
            throw new RuntimeException("create mqttClient failed, exception is " + e.getMessage());
        }
    }



}
