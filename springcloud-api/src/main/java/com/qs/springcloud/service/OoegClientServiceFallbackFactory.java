package com.qs.springcloud.service;


import com.qs.springcloud.pojo.Ooeg;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


//降级
@Component
public class OoegClientServiceFallbackFactory implements FallbackFactory {

    @Override
    public OoegClientService create(Throwable throwable) {
        return new OoegClientService() {
            @Override
            public Ooeg queryById(String id) {
                return new Ooeg()
                        .setOoeg001(id)
                        .setOoegstus(id+"没有对应的信息，客户端提供了降级的信息，这个服务已经被关闭")
                        .setOoeg003(id+"错误信息同上");
            }

            @Override
            public List<Ooeg> queryAll() {
                return null;
            }
        };
    }
}
