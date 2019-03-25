package cn.edu.nju.lc.network.service;

import cn.edu.nju.lc.data.CollectData;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Value("${spring.mqtt.qos}")
    private int qos;
    @Value("${spring.mqtt.topic}")
    private String topic;

    @Autowired
    private MqttClient mqttClient;


    public void send(CollectData collectData) {
        try {
            String content = JSONObject.toJSONString(collectData);
            // 创建消息
            MqttMessage message = new MqttMessage(content.getBytes());
            // 设置消息的服务质量
            message.setQos(qos);
            // 发布消息
            mqttClient.publish(topic, message);
        } catch (MqttException me) {
            logger.error("send msg failed, exception is " + me.getMessage());
        }
    }

}
