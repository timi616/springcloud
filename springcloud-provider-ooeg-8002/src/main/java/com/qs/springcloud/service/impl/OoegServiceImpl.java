package com.qs.springcloud.service.impl;

import com.qs.springcloud.mapper.OoegMapper;
import com.qs.springcloud.pojo.Ooeg;
import com.qs.springcloud.service.OoegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OoegServiceImpl implements OoegService {

    @Autowired
    OoegMapper ooegMapper;

    @Override
    public Ooeg queryById(String id) {
        return ooegMapper.queryById(id);
    }

    @Override
    public List<Ooeg> queryAll() {
        return ooegMapper.queryAll();
    }
}
