package cn.edu.nju.lc;

import cn.edu.nju.lc.handler.DataPreHandler;
import cn.edu.nju.lc.serial.SerialListener;
import cn.edu.nju.lc.serial.SerialTool;
import gnu.io.SerialPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SerialListenerApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SerialListenerApplication.class, args);
        String portName = SerialTool.getExperimentPortName();
        SerialPort serialPort = SerialTool.openPort(portName, 9600);
        SerialListener listener = new SerialListener(serialPort);
        String data;
        DataPreHandler dataPreHandler = applicationContext.getBean(DataPreHandler.class);
        while (true) {
            if ((data = listener.receive()) != null) {
                dataPreHandler.handle(data);
            }
        }
    }




}
