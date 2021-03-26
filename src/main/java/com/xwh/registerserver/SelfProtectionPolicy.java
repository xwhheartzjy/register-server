package com.xwh.registerserver;

public class SelfProtectionPolicy {

    private SelfProtectionPolicy() {

    }

    private static SelfProtectionPolicy instance = new SelfProtectionPolicy();

    public static SelfProtectionPolicy getInstance() {
        return instance;
    }

    /**
     * 期望的一个心跳次数
     */
    private long expectedHeartbeatRate = 0L;

    private long expectedHeartbeatThreshold = 0L;


    public Boolean isEnabled() {
        HeartbeatMessuredRate heartbeatMessuredRate = HeartbeatMessuredRate.getInstance();
        long latestMinuteHeartbeatRate = heartbeatMessuredRate.get();
        if (latestMinuteHeartbeatRate < this.expectedHeartbeatThreshold) {
            System.out.println("【自我保护机制开启】最近一分钟心跳次数=" + latestMinuteHeartbeatRate +
                    "，期望心跳次数=" + this.expectedHeartbeatThreshold);
            return true;

        }
        System.out.println("【自我保护机制未开启】最近一分钟心跳次数=" + latestMinuteHeartbeatRate +
                "，期望心跳次数=" + this.expectedHeartbeatThreshold);
        return false;
    }


    public long getExpectedHeartbeatRate() {
        return expectedHeartbeatRate;
    }

    public void setExpectedHeartbeatRate(long expectedHeartbeatRate) {
        this.expectedHeartbeatRate = expectedHeartbeatRate;
    }

    public long getExpectedHeartbeatThreshold() {
        return expectedHeartbeatThreshold;
    }

    public void setExpectedHeartbeatThreshold(long expectedHeartbeatThreshold) {
        this.expectedHeartbeatThreshold = expectedHeartbeatThreshold;
    }
}
