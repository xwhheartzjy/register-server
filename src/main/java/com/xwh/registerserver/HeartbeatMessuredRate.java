package com.xwh.registerserver;

/**
 * @Author xuwenhui
 * @Description 统计心跳次数
 * @Date 2021/3/25 9:29 上午
 **/

public class HeartbeatMessuredRate {
    //单例模式
    private static HeartbeatMessuredRate instance = new HeartbeatMessuredRate();

    private HeartbeatMessuredRate() {

    }

    public static HeartbeatMessuredRate getInstance() {
        return instance;
    }

    /**
     * 最近一分钟的心跳数
     */
    private long latestMinuteHeartbeatRate = 0L;
    /**
     * 最近一分钟的时间戳
     */
    private long latestMinuteTimestamp = System.currentTimeMillis();

    public synchronized void increment() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp - latestMinuteTimestamp > 60 * 1000) {
            latestMinuteHeartbeatRate = 0L;
            this.latestMinuteTimestamp = System.currentTimeMillis();
        }
        latestMinuteHeartbeatRate++;
    }

    public synchronized long get() {
        return this.latestMinuteHeartbeatRate;
    }


}
