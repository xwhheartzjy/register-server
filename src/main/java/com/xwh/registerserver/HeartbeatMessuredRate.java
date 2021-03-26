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
        Daemon daemon = new Daemon();
        daemon.setDaemon(true);
        daemon.start();
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

    public void increment() {
        synchronized (HeartbeatMessuredRate.class) {
            latestMinuteHeartbeatRate++;
        }

    }

    public synchronized long get() {
        return this.latestMinuteHeartbeatRate;
    }

    private class Daemon extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (HeartbeatMessuredRate.class) {
                        long currentTimestamp = System.currentTimeMillis();

                        if (currentTimestamp - latestMinuteTimestamp > 60 * 1000) {
                            latestMinuteHeartbeatRate = 0L;
                            latestMinuteTimestamp = System.currentTimeMillis();
                        }
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
