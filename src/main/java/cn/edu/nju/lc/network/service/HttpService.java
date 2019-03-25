package cn.edu.nju.lc.network.service;

import cn.edu.nju.lc.data.CollectData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService {

    @Autowired
    private RestTemplate restTemplate;

    public void send(CollectData collectData) {
        restTemplate.put("http://localhost:10000/non_stream", collectData);
    }

}
