package com.fansin.seckill.mybatis.mapper;

import com.fansin.seckill.mybatis.entity.SuccessSeckilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by zhaofeng on 17-4-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessSeckilledMapperTest {
    @Autowired
    SuccessSeckilledMapper mapper;


    @Test
    public void insertSuccessSeckill() throws Exception {
        System.out.println("重复插入返回结果为0,正常插入为1");
        int rs1 = mapper.insertSuccessSeckill(1002,18188238234l);
        System.out.println("秒杀1 "+rs1);
        rs1 = mapper.insertSuccessSeckill(1003,18488533234l);
        System.out.println("秒杀2 "+rs1);
        rs1 = mapper.insertSuccessSeckill(1001,18688538234l);
        System.out.println("秒杀3 "+rs1);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessSeckilled successSeckilled = mapper.queryByIdWithSeckill(1001,18688538234l);
        System.out.println("查询结果:"+successSeckilled);
        System.out.println("查询结果:"+successSeckilled.getSecKill());
    }

}