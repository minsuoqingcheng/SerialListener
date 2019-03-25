package cn.edu.nju.lc;

import cn.edu.nju.lc.serial.SerialListener;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SerialListener.class)
public class ApplicationTest {

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void testRestTemplate() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test", "test");
        restTemplate.put("http://localhost:10000/test", jsonObject);
    }



}
