package com.fansin.seckill.service;

import com.fansin.seckill.dto.SeckillExecution;
import com.fansin.seckill.dto.SeckillExposer;
import com.fansin.seckill.exceptions.RepeatSeckillException;
import com.fansin.seckill.exceptions.SeckillCloseException;
import com.fansin.seckill.mybatis.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhaofeng on 17-4-10.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService service;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = service.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getSeckillById() throws Exception {
        long seckillId = 1000l;
        Seckill seckill = service.getSeckillById(seckillId);
        logger.info("seckill={}",seckill);
    }
//    @Rollback(false)
    @Transactional
    @Test
    public void testSeckill() throws Exception {
        long seckillId = 1002l;
        SeckillExposer exposer = service.exportSeckillUrl(seckillId);
        logger.info("exposer={}",exposer);
        if(exposer != null && exposer.isExposed()){
            long userPhone = 13256482235l;
            try {
                SeckillExecution execution = service.executeSeckill(seckillId,userPhone,exposer.getMd5());
                logger.info("SeckillExecution={}",execution);
            }catch (RepeatSeckillException e){
                logger.error("重复秒杀");
            }catch (SeckillCloseException e){
                logger.error("秒杀结束");
            }
        }else{
            logger.warn("秒杀未开始!");
        }

    }

}