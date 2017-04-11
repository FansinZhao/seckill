package com.fansin.seckill.dto;

/**
 *
 * 业务无关
 * Created by zhaofeng on 17-4-10.
 */
public class SeckillExposer {

    /*秒杀是否开启*/
    private boolean exposed =false;
    /*秒杀商品*/
    private long seckillId;
    /*MD5*/
    private String md5;
    /*秒杀开始时间*/
    private long start;
    /*秒杀结束时间*/
    private long end;
    /*当前系统时间*/
    private long now;

    public SeckillExposer(boolean exposed, long seckillId, String md5) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.md5 = md5;
    }

    public SeckillExposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public SeckillExposer(boolean exposed,long seckillId, long start, long end, long now) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.start = start;
        this.end = end;
        this.now = now;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }


    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    @Override
    public String toString() {
        return "SeckillExposer{" +
                "exposed=" + exposed +
                ", seckillId=" + seckillId +
                ", md5='" + md5 + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", now=" + now +
                '}';
    }
}
