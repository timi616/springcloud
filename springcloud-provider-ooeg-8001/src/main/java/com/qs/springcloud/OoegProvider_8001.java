package com.qs.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

//启动类
@SpringBootApplication
@EnableEurekaClient //在服务启动后自动注册到Eureka中
@EnableDiscoveryClient //服务发现
public class OoegProvider_8001 {
    public static void main(String[] args) {
        SpringApplication.run(OoegProvider_8001.class,args);
    }

}
