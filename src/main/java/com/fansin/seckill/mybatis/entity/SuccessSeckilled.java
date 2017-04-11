package com.fansin.seckill.mybatis.entity;

import java.util.Date;

/**
 * Created by zhaofeng on 17-4-9.
 */
public class SuccessSeckilled {

    private long seckillId;
    private long userPhone;
    private byte state;
    private Date createTime;

    private Seckill secKill;


    public Seckill getSecKill() {
        return secKill;
    }

    public void setSecKill(Seckill secKill) {
        this.secKill = secKill;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuccessSeckilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", secKill=" + secKill +
                '}';
    }
}
