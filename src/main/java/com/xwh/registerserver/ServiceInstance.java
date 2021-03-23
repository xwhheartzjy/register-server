package com.xwh.registerserver;

public class ServiceInstance {
    private static final Long NOT_ALIVE_TIME = 90 * 1000L;

    private String serviceName;

    private String ip;

    private String hostname;

    private String serviceInstanceId;

    private Integer port;

    private Lease lease;

    public ServiceInstance() {
        this.lease = new Lease();
    }

    public void renew() {
        this.lease.renew();
    }

    public Boolean isAlive() {
        return this.lease.isAlive();
    }

    private class Lease {

        private volatile Long latestHeartbeatTime = System.currentTimeMillis();


        public void renew() {
            this.latestHeartbeatTime = System.currentTimeMillis();
            System.out.println("服务实例：" + serviceInstanceId + "进行续约" + this.latestHeartbeatTime);
        }

        public Boolean isAlive() {
            Long currentTime = System.currentTimeMillis();

            if (currentTime - this.latestHeartbeatTime >= NOT_ALIVE_TIME) {
                System.out.println("服务实例:"+serviceInstanceId+"不在存活");
                return false;
            }
            return true;
        }
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "serviceName='" + serviceName + '\'' +
                ", ip='" + ip + '\'' +
                ", hostname='" + hostname + '\'' +
                ", serviceInstanceId='" + serviceInstanceId + '\'' +
                ", port=" + port +
                ", lease=" + lease +
                '}';
    }
}
