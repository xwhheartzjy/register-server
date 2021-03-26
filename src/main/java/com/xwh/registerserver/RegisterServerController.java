package com.xwh.registerserver;

import java.util.Map;

/**
 * 模拟接收请求
 */
public class RegisterServerController {

    private Registry registry = Registry.getInstance();

    public RegisterResponse regist(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {

            ServiceInstance serviceInstance = new ServiceInstance();
            serviceInstance.setPort(registerRequest.getPort());
            serviceInstance.setHostname(registerRequest.getHostName());
            serviceInstance.setIp(registerRequest.getIp());
            serviceInstance.setServiceInstanceId(registerRequest.getInstanceId());
            serviceInstance.setServiceName(registerRequest.getServiceName());

            registry.register(serviceInstance);

            //更新自我保护阈值
            synchronized (SelfProtectionPolicy.class) {
                SelfProtectionPolicy selfProtectionPolicy = SelfProtectionPolicy.getInstance();
                selfProtectionPolicy.setExpectedHeartbeatRate(selfProtectionPolicy.getExpectedHeartbeatRate() + 2);
                selfProtectionPolicy.setExpectedHeartbeatThreshold(
                        (long)(selfProtectionPolicy.getExpectedHeartbeatRate() * 0.85));
            }

            registerResponse.setStatus(RegisterResponse.SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            registerResponse.setStatus(RegisterResponse.FAILURE);
        }
        return null;
    }

    public HeartBeatResponce heartbeat(HeartBeatRequest heartBeatRequest) {
        HeartBeatResponce heartBeatResponce = new HeartBeatResponce();
        try {

            ServiceInstance serviceInstance = registry.getServiceInstance(heartBeatRequest.getServiceName()
                    , heartBeatRequest.getInstanceId());

            serviceInstance.renew();
            //记录每分钟的心跳数，自我保护机制
            HeartbeatMessuredRate heartbeatMessuredRate = HeartbeatMessuredRate.getInstance();
            heartbeatMessuredRate.increment();

            heartBeatResponce.setStatus(HeartBeatResponce.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            heartBeatResponce.setStatus(HeartBeatResponce.FAILURE);
        }
        return heartBeatResponce;
    }
    /**
     * 拉取服务注册表
     * @return
     */
    public Map<String, Map<String, ServiceInstance>> fetchServiceRegistry() {
        return registry.getRegistry();
    }

    /**
     * 服务下线
     */
    public void cancel(String serviceName, String serviceInstanceId) {
        // 从服务注册中摘除实例
        registry.remove(serviceName, serviceInstanceId);
        //更新自我保护阈值
        synchronized (SelfProtectionPolicy.class) {
            SelfProtectionPolicy selfProtectionPolicy = SelfProtectionPolicy.getInstance();
            selfProtectionPolicy.setExpectedHeartbeatRate(selfProtectionPolicy.getExpectedHeartbeatRate() - 2);
            selfProtectionPolicy.setExpectedHeartbeatThreshold(
                    (long)(selfProtectionPolicy.getExpectedHeartbeatRate() * 0.85));
        }
    }



}
