package cn.edu.nju.lc.handler;

import cn.edu.nju.lc.data.CollectData;
import cn.edu.nju.lc.data.DataType;
import cn.edu.nju.lc.network.service.HttpService;
import cn.edu.nju.lc.network.service.MqttService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class DataPreHandler {

    private Logger logger = LoggerFactory.getLogger(DataPreHandler.class);

    private Executor executors = Executors.newFixedThreadPool(4);

    @Autowired
    private HttpService httpService;
    @Autowired
    private MqttService mqttService;


    public void handle(String data) {
        if (data.startsWith("{") && data.endsWith("}")) {
            executors.execute(() -> {
                CollectData collectData = JSONObject.toJavaObject(JSON.parseObject(data), CollectData.class);
                collectData.setTime(System.currentTimeMillis());    //设置获取到的时间
                DataType dataType = DataType.getDataType(collectData.getType());
                switch (dataType) {
                    case NON_STREAM:
                        httpService.send(collectData);
                        break;
                    case STREAM:
                        mqttService.send(collectData);
                        break;
                    default:
                        logger.warn("get Unknown data type!!!");
                }
            });
        }
    }

}
