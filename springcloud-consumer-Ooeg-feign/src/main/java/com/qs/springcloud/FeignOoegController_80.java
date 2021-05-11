package com.qs.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient //Ribbon 和 Eureka 整合之后，客户端可以直接调用，不用关心IP端口和地址
@EnableFeignClients(basePackages = {"com.qs.springcloud"})
@ComponentScan("com.qs.springcloud")
//@RibbonClient(name = "SPRINGCLOUD-PROVIDER-OOEG",configuration = QsRule.class)  //在微服务启动的时候就能去加载我们自定义的Ribbon类
public class FeignOoegController_80 {
    public static void main(String[] args) {
        SpringApplication.run(FeignOoegController_80.class,args);
    }
}
