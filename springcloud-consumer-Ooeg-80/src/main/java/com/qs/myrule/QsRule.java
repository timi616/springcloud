package com.qs.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QsRule {
    /*
    * 将ConfigBean 中方法提取到这里
    *
    * 将默认的new RandomRule() 改成自己的
    * */
    @Bean
    public IRule myRule(){
//        return new RandomRule(); //使用系统提供的
        return new QsRandomRule(); //自己定义的
    }
}
