package com.qs.springcloud.mapper;


import com.qs.springcloud.pojo.Ooeg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OoegMapper {

    //根据ID 差员工
    //public Ooeg queryById(Long id);
    Ooeg queryById(@Param("id") String id); //可以不写public

    //查全部员工
    //public List<Ooeg> query();
    List<Ooeg> queryAll(); //可以不写public
}
