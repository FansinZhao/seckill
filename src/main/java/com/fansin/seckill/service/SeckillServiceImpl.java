package com.fansin.seckill.service;

import com.fansin.seckill.dto.SeckillExecution;
import com.fansin.seckill.dto.SeckillExposer;
import com.fansin.seckill.enums.SeckillState;
import com.fansin.seckill.exceptions.RepeatSeckillException;
import com.fansin.seckill.exceptions.SeckillCloseException;
import com.fansin.seckill.exceptions.SeckillException;
import com.fansin.seckill.mybatis.entity.Seckill;
import com.fansin.seckill.mybatis.entity.SuccessSeckilled;
import com.fansin.seckill.mybatis.mapper.SeckilllMapper;
import com.fansin.seckill.mybatis.mapper.SuccessSeckilledMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaofeng on 17-4-10.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    //md5盐值,越复杂越好
    private final String salt = "github.com/fansinZhao/seckill";

    @Autowired
    private SeckilllMapper seckilllMapper;

    @Autowired
    private SuccessSeckilledMapper successMapper;

    /**
     * 获取所有的秒杀产品
     */
    @Override
    public List<Seckill> getSeckillList() {

        List<Seckill> list = seckilllMapper.queryAll(0,4);
        logger.debug("查询出结果数量:"+list.size());
        return list;
    }

    /**
     * 获取单个秒杀产品
     *
     * @param seckillId
     */
    @Override
    public Seckill getSeckillById(long seckillId) {

        Seckill seckill = seckilllMapper.queryById(seckillId);

        if(seckill == null){
            logger.error("查询不到秒杀产品,seckillId="+seckillId);
        }

        return seckill;
    }

    /**
     * 秒杀暴露地址
     * 如果秒杀开始,则输出秒杀地址
     * 否则,输出系统时间和秒杀开始结束时间
     *
     * @param seckillId
     * @return
     */
    @Override
    public SeckillExposer exportSeckillUrl(long seckillId) {

        Seckill seckill = getSeckillById(seckillId);
        if(seckill == null){
            logger.error("没有找到对应的秒杀产品");
            return  new SeckillExposer(false,seckillId);
        }
        Date now = new Date();
        Date start = seckill.getStartTime();
        Date end = seckill.getEndTime();
        if(now.getTime() > end.getTime() || now.getTime() < start.getTime()){//已结束/未开始
            //活动结束或未开始
            return  new SeckillExposer(false,seckillId,now.getTime(),start.getTime(),end.getTime());
        }
        //活动开启
        String md5 = getMD5(seckillId);
        return new SeckillExposer(true,seckillId,md5);
    }

    private String getMD5(long key){
        String content = key+"/"+salt;
        return DigestUtils.md5DigestAsHex(content.getBytes());
    }

    /**
     * [重点]执行秒杀操作
     * 出现运行时异常,回滚
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    /*
    * 1 约定统一的事务使用方式,包含两个及以上的数据库写操作
    * 2 事务内只保证只有数据库写操作的耗时行为,而不应该包含其他超时行为
    * 3 事务应该简单化,只对必要的写库操作进行事务控制
    *
    * */
    @Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatSeckillException, SeckillCloseException, SeckillException {

        try {
            //1 校验MD5
            if(null == md5 || !getMD5(seckillId).equals(md5)){
                throw  new SeckillException("数据篡改");
            }
            //逻辑 减库存+增加秒杀记录
            //2 减库存
            Date now  = new Date();
            int rs = seckilllMapper.reduceNumber(seckillId,now);
            if(rs > 0){
                //3 插入秒杀成功详情表
                int is = successMapper.insertSuccessSeckill(seckillId,userPhone);
                if (is > 0){
                    //秒杀成功
                    SuccessSeckilled successSeckilled = successMapper.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillState.SUCCESS,successSeckilled);
                }else {
                    //重复秒杀
                    throw new RepeatSeckillException("重复秒杀");
                }
            }else {
                throw  new SeckillCloseException("秒杀结束!");
            }
        }catch (RepeatSeckillException e1){
            throw e1;
        } catch (SeckillCloseException e2){
            throw e2;
        }catch (Exception e){
            logger.error("秒杀系统异常",e);
            throw new SeckillException("秒杀系统异常",e);
        }

    }
}
