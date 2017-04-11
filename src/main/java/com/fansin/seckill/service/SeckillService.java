package com.fansin.seckill.service;

import com.fansin.seckill.dto.SeckillExecution;
import com.fansin.seckill.dto.SeckillExposer;
import com.fansin.seckill.exceptions.RepeatSeckillException;
import com.fansin.seckill.exceptions.SeckillCloseException;
import com.fansin.seckill.exceptions.SeckillException;
import com.fansin.seckill.mybatis.entity.Seckill;

import java.util.List;

/**
 *
 * 以接口"使用者"的角度去设计接口
 * 1 方法粒度 2 方法参数 3 return参数
 * Created by zhaofeng on 17-4-10.
 */
public interface SeckillService {

    /**
     * 获取所有的秒杀产品
     * */
    List<Seckill> getSeckillList();
    /**
     * 获取单个秒杀产品
     * */
    Seckill getSeckillById(long seckillId);

    /**
     * 秒杀暴露地址
     * 如果秒杀开始,则输出秒杀地址
     * 否则,输出系统时间和秒杀开始结束时间
     * @param seckillId
     * @return
     */
    SeckillExposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * */
     SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatSeckillException,SeckillCloseException,SeckillException;

}
