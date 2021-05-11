package com.qs.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;  //实现序列化


@Data
@NoArgsConstructor //无参构造器 注解
@Accessors(chain = true) //true 支持链式写法
public class Ooeg implements Serializable { //Ooeg 实体类  orm:类表关系映射

    private String ooeg001;
    private String ooegstus;
    private String ooeg003;

    //有参 构造器
    public Ooeg(String ooeg001, String ooegstus, String ooeg003) {
        this.ooeg001 = ooeg001;
        this.ooegstus = ooegstus;
        this.ooeg003 = ooeg003;
    }

/*
    不加注解时
    Ooeg ooeg = new Ooeg();
    ooeg.setOoeg001('001');
    ooeg.setOoegstus('Y');
    ooeg.setOoeg003('1');

    链式写法：
    Ooeg ooeg = new Ooeg();
    ooeg.setOoeg001('001').setOoegstus('Y').setOoeg003('1');

 */
}
