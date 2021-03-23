package com.xwh.registerserver;

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

            heartBeatResponce.setStatus(HeartBeatResponce.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            heartBeatResponce.setStatus(HeartBeatResponce.FAILURE);
        }
        return heartBeatResponce;
    }



}
