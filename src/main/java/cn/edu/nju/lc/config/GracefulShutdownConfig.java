package cn.edu.nju.lc.config;

import org.apache.catalina.connector.Connector;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

@Configuration
public class GracefulShutdownConfig {


    @Bean
    public GracefulShutdown gracefulShutdown() {
        return new GracefulShutdown();
    }

    /**
     * 配置tomcat
     *
     * @return
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(gracefulShutdown());
        return tomcat;
    }

    /**
     * 优雅关闭 Spring Boot。容器必须是 tomcat
     */
    private class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

        private Logger logger = LoggerFactory.getLogger(GracefulShutdown.class);

        @Override
        public void customize(Connector connector) {

        }

        @Override
        public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
            ApplicationContext applicationContext = contextClosedEvent.getApplicationContext();
            MqttClient mqttClient = applicationContext.getBean(MqttClient.class);
            try {
                mqttClient.disconnect();
                mqttClient.close();
            } catch (MqttException e) {
                logger.error("close mqttClient failed, exception is: "+e.getMessage());
            }

            logger.info("call GracefulShutdown when application close!");
        }


    }

}
