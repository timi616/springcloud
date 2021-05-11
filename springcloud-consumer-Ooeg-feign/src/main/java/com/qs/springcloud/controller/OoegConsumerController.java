package com.qs.springcloud.controller;

import com.qs.springcloud.pojo.Ooeg;
import com.qs.springcloud.service.OoegClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OoegConsumerController {

    //消费者 不应该有service层，所以直接调用提供者的方式（springcloud-provider-ooeg-8001

    //RestTemplate 模板   我们可以直接调用，前提时注册到Spring中（当前模块中的ConfigBean 方法）。可以直接使用@Autowired注入

    //RestTemplate参数(url,实体：Map,Class<T> responseType)

    /*
    Ribbon 方式
    @Autowired
    private RestTemplate restTemplate;  //提供多种便捷访问远程http服务的方法，简单的Restful服务模板


    //使用Ribbon负载均衡，这里的 REST_URL_PREFIX 变量 应该是eureka注册中心的服务名称
    //直接使用地址
    //private static final String REST_URL_PREFIX = "http://localhost:8001";
    private static final String REST_URL_PREFIX = "http://SPRINGCLOUD-PROVIDER-OOEG";

    @RequestMapping("/consumer/ooeg/get/{id}")
    public Ooeg get(@PathVariable("id") String id){
        //没办吧直接返回，所以要到提供者里拿（springcloud-provider-ooeg-8001 模块）
        // 地址：http://localhost:8001/ooeg/get/012

        //实体使用getForEntity(),对象使用getForObject()  （getForObject()通用）
        //使用getForObject(URI url,Class<T> responseType)
        return restTemplate.getForObject(REST_URL_PREFIX+"/ooeg/get/"+id,Ooeg.class);
    }

    @RequestMapping("/consumer/ooeg/list")
    public List<Ooeg> list(){
        return restTemplate.getForObject(REST_URL_PREFIX+"/ooeg/list/",List.class);
    }
*/

    //Feign 方式
    @Autowired
    private OoegClientService ooegClientService = null;

    @GetMapping("/comsumer/ooeg/get/{id}")
    public Ooeg get(@PathVariable("id") String id){
        return this.ooegClientService.queryById(id);
    }

    @GetMapping("/comsumer/ooeg/list")
    public List<Ooeg> list(){
        System.out.println("queryAll");
        return this.ooegClientService.queryAll();
    }
}
