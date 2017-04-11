package com.fansin.seckill.mybatis.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by zhaofeng on 17-4-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckilllMapperTest {

    @Autowired
    private SeckilllMapper mapper;

    @Test
    public void reduceNumber() throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH,9);
        c.set(Calendar.MONTH,Calendar.APRIL);
        int rs = mapper.reduceNumber(1003,new Date());
        System.out.println("减库存:"+rs);
    }

    @Test
    public void queryById() throws Exception {
        System.out.println(mapper.queryById(1000l));
    }

    @Test
    public void queryAll() throws Exception {
        System.out.println(mapper.queryAll(0,4));
    }

}