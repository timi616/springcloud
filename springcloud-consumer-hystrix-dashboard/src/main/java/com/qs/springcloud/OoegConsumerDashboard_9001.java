package com.qs.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@SpringBootApplication
@EnableHystrixDashboard //添加对熔断的支持
public class OoegConsumerDashboard_9001 {
    public static void main(String[] args) {
        SpringApplication.run(OoegConsumerDashboard_9001.class,args);
    }
}
