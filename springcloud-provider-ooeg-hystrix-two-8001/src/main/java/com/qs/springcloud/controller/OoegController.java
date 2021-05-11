package com.qs.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qs.springcloud.pojo.Ooeg;
import com.qs.springcloud.service.OoegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//提供Restful 服务
@RestController
public class OoegController {

    @Autowired
    private OoegService ooegService;


    @GetMapping("/ooeg/get/{id}")
    @HystrixCommand(fallbackMethod = "hystrixGet")
    public Ooeg queryById(@PathVariable("id") String id){
        System.out.println("开始-----------");

        Ooeg ooeg = ooegService.queryById(id);
        if (ooeg == null){
            throw new RuntimeException(id+"信息不存在");
        }
        return ooeg;
    }

    @GetMapping("/ooeg/list")
    public List<Ooeg> queryAll(){

        return ooegService.queryAll();
    }

    //方法有问题时的备选方法  可以直接是用try catch 或 异常，这里是要测试Hystrix
    public Ooeg hystrixGet(@PathVariable("id") String id){
        return new Ooeg()
                .setOoeg001(id)
                .setOoeg003(id+"信息异常的提示方法，该位置在hystrix")
                .setOoegstus(id+"错误信息同上");
    }

}
