package com.xwh.registerserver;

import java.util.HashMap;
import java.util.Map;

public class Registry {

    private static Registry instance = new Registry();

    private Registry() {

    }

    /**
     * 注册表
     * key:serviceName,value:这个服务名称下面所有的服务实例
     * key:服务实例id，value：服务实例本身
     *
     */
    private Map<String, Map<String, ServiceInstance>> registry = new HashMap<String,Map<String,ServiceInstance>>();

    public void register(ServiceInstance serviceInstance) {
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceInstance.getServiceName());

        if (serviceInstanceMap == null) {
            serviceInstanceMap = new HashMap<String, ServiceInstance>();
            registry.put(serviceInstance.getServiceName(), serviceInstanceMap);
        }

        serviceInstanceMap.put(serviceInstance.getServiceInstanceId(), serviceInstance);

        System.out.println("服务实例："+serviceInstance.toString()+"。注册成功.........");

    }

    public ServiceInstance getServiceInstance(String serviceName,String serviceInstanceId) {
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
        return serviceInstanceMap.get(serviceInstanceId);
    }

    public static Registry getInstance() {
        return instance;
    }

    public Map<String, Map<String, ServiceInstance>> getRegistry() {
        return registry;
    }

    public void remove(String serviceName, String serviceInstanceId) {
        System.out.println("服务实例【" + serviceInstanceId + "】，从注册表中进行摘除");
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
        serviceInstanceMap.remove(serviceInstanceId);
    }
}
