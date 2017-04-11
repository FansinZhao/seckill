package com.fansin.seckill.enums;

/**
 * 秒杀状态枚举类
 * Created by zhaofeng on 17-4-10.
 */
public enum  SeckillState {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    private int state;
    private String stateInfo;

    SeckillState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
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

    public SeckillState stateOf(int index){
        for (SeckillState seckillState : values()) {
            if(seckillState.getState() == index){
                return seckillState;
            }
        }
        return null;
    }
}
