package com.fansin.seckill.mybatis.mapper;

import com.fansin.seckill.mybatis.entity.SuccessSeckilled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhaofeng on 17-4-9.
 */
@Mapper
public interface SuccessSeckilledMapper {

    /**
     * 插入秒杀成功产品,可以过滤重复
     * @param seckillId
     * @param userPhone
     * @return 如果返回值为1,表示插入成功,否则,插入失败
     */
    int insertSuccessSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据用户id返回秒杀记录
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessSeckilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
