package com.xwh.registerserver;

import java.util.Map;

public class ServiceAliveMonitorDaemon {

    /**
     * 检查服务实例是否存活的间隔
     */
    private static final Long CHECK_ALIVE_INTERVAL = 60 * 1000L;

    private Daemon daemon = new Daemon();

    public void start() {
        daemon.start();
    }


    private class Daemon extends Thread{

        private Registry registry = Registry.getInstance();

        @Override
        public void run() {
            Map<String, Map<String,ServiceInstance>> registryMap = null;

            while (true) {
                try {
                    registryMap = registry.getRegistry();

                    for (String serviceName : registryMap.keySet()) {
                        Map<String, ServiceInstance> instanceMap = registryMap.get(serviceName);

                        for (ServiceInstance serviceInstance : instanceMap.values()) {
                            if (!serviceInstance.isAlive()) {
                                registry.remove(serviceName, serviceInstance.getServiceInstanceId());

                            }
                        }
                    }

                    Thread.sleep(CHECK_ALIVE_INTERVAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }



}
