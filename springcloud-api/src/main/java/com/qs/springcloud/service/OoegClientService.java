package com.qs.springcloud.service;

import com.qs.springcloud.pojo.Ooeg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component //注册到Spring中
//@FeignClient(value = "SPRINGCLOUD-PROVIDER-OOEG") //value 微服务名称
@FeignClient(value = "SPRINGCLOUD-PROVIDER-OOEG",fallbackFactory = OoegClientServiceFallbackFactory.class) //测试Hystrix降级添加参数fallbackFactory
public interface OoegClientService {

    //根据ID 差员工
    //public Ooeg queryById(Long id);
    @GetMapping("/ooeg/get/{id}")
    Ooeg queryById(@PathVariable("id") String id); //可以不写public

    //查全部员工
    //public List<Ooeg> query();
    @GetMapping("/ooeg/list")
    List<Ooeg> queryAll(); //可以不写public
}
