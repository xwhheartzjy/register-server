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
