package com.qs.springcloud.controller;

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

    @Autowired
    private DiscoveryClient client;

    @GetMapping("/ooeg/get/{id}")
    public Ooeg queryById(@PathVariable("id") String id){
        return ooegService.queryById(id);
    }

    @GetMapping("/ooeg/list")
    public List<Ooeg> queryAll(){
        return ooegService.queryAll();
    }

    //注册进来的服务，获取一下信息
    @GetMapping("/Ooeg/discovery")
    public Object discovery(){
        //获取微服的列表清单
        List<String> services = client.getServices();
        System.out.println("微服务清单："+services);

        //得到一个具体的微服务信息,通过具体的微服务id获取
        List<ServiceInstance> instances = client.getInstances("SPRINGCLOUD-PROVIDER-OOEG");
        for (ServiceInstance instance:instances) {
            System.out.println(
                    instance.getHost()+"\t"+
                    instance.getPort()+"\t"+
                    instance.getUri()+"\t"+
                    instance.getServiceId()
            );
        }

        return  this.client;
    }

}
