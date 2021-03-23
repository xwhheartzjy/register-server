package com.xwh.registerserver;

import java.util.UUID;

public class RegisterServer {

    public static void main(String[] args) throws InterruptedException {
        RegisterServerController controller = new RegisterServerController();
        //模拟发起一个服务注册的请求，模拟web
        RegisterRequest registerRequest = new RegisterRequest();

        String serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
        registerRequest.setHostName("inventory-service-01");
        registerRequest.setIp("192.168.31.208");
        registerRequest.setPort(9000);
        registerRequest.setInstanceId(serviceInstanceId);
        registerRequest.setServiceName("inventory-service");

        controller.regist(registerRequest);

        // 模拟进行一次心跳，完成续约
        HeartBeatRequest heartbeatRequest = new HeartBeatRequest();
        heartbeatRequest.setServiceName("inventory-service");
        heartbeatRequest.setInstanceId(serviceInstanceId);

        controller.heartbeat(heartbeatRequest);

        //开启一个后台线程检查微服务状态
        ServiceAliveMonitorDaemon serviceAliveMonitorDaemon = new ServiceAliveMonitorDaemon();
        serviceAliveMonitorDaemon.start();

        while (true) {
            Thread.sleep(30*1000);
        }
    }
}
