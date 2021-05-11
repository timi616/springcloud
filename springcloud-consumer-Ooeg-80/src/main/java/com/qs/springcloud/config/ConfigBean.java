package com.qs.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration //相当于spring 中applicationContext.xml
public class ConfigBean {

    /*
    这样就可以把RestTemplate模板注册到Bean中
    */

    //配置负载均衡实现RestTemplate 添加注解@LoadBalanced
    @Bean
    @LoadBalanced //Ribbon
    public RestTemplate getRestTemplate(){
        return  new RestTemplate(); //使用默认模板
    }



    /*
     * 自定义Ribbon 算法
     *
     * 1.IRule
     * 2.RoundRobinRule 轮询
     * 3.RandomRule 随机
     * 4.Retry 会先按照轮询获取服务，如果服务获取失败则会在指定的时间内进行重试
     * 5.AvailabilityFilteringRule : 会先过滤掉 跳闸、访问故障的服务，对剩下的服务进行轮询
     *
     * */
//    @Bean
//    public IRule myRule(){
//        return new RandomRule(); //使用系统提供的
//    }

}
