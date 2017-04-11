package com.fansin.seckill.dto;

import com.fansin.seckill.enums.SeckillState;
import com.fansin.seckill.mybatis.entity.SuccessSeckilled;

/**
 *
 * 秒杀成功结果
 * Created by zhaofeng on 17-4-10.
 */
public class SeckillExecution {
    /*秒杀产品*/
    private long seckillId;
    /*秒杀状态*/
    private int state;
    /*秒杀状态描述*/
    private String stateInfo;
    /*秒杀成功结果*/
    private SuccessSeckilled seckilled;

    public SeckillExecution(long seckillId, SeckillState state, SuccessSeckilled seckilled) {
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.seckilled = seckilled;
    }

    public SeckillExecution(long seckillId, SeckillState state) {
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }

    public SeckillExecution(long seckillId, int state, String stateInfo, SuccessSeckilled seckilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.seckilled = seckilled;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessSeckilled getSeckilled() {
        return seckilled;
    }

    public void setSeckilled(SuccessSeckilled seckilled) {
        this.seckilled = seckilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", seckilled=" + seckilled +
                '}';
    }
}
