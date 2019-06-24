package com.merchant.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/6/4
 */
@SpringBootApplication
public class OrderDaoApplication {


    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            SpringApplication app = new SpringApplication(OrderDaoApplication.class);
            app.setDefaultProperties(properties);
            app.run(args);
            System.out.println("success");
        }catch (Exception e){
            System.out.println("Fail to start OrderApplication ..."+e.toString());
        }
    }
}
