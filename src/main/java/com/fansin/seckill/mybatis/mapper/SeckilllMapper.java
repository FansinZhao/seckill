package com.fansin.seckill.mybatis.mapper;

import com.fansin.seckill.mybatis.entity.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaofeng on 17-4-9.
 */
@Mapper
public interface SeckilllMapper {


    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果返回>0,减库存成功,否则失败
     */
    int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);

    /**
     * 根据id查询
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);


    /**
     * 根据偏移量查询所有
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

}
