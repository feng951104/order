package com.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.IOException;
import java.util.Properties;

/**
 * Description:user 启动类
 *
 * @author wangyf
 * @date 2019/4/16
 */

@SpringBootApplication
@EnableEurekaClient
public class OrderApplication {

    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(OrderApplication.class.getResourceAsStream("/system.properties"));


            SpringApplication app = new SpringApplication(OrderApplication.class);
            app.setDefaultProperties(properties);
            app.run(args);
            System.out.println("success");
        } catch (IOException e) {
            System.out.println("Fail to start UserApplication due to exception:" + e.toString());
        }
    }

}
