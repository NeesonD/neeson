package com.neeson.rpc.client;

import com.neeson.rpc.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/2 23:25
 */
@Slf4j
public class ServiceDiscovery {

    private volatile List<String> dataList = new ArrayList<>();

    private String registryAddress;

    private CuratorFramework curatorFramework;

    public ServiceDiscovery(String registryAddress) throws Exception {
        this.registryAddress = registryAddress;


        byte[] content = curatorFramework.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听器watchedEvent：" + watchedEvent);
            }
        }).forPath(Constant.ZK_REGISTRY_PATH + "/");

    }

    public String discover() {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            if (size == 1) {
                data = dataList.get(0);
                log.debug("using only data: {}", data);
            } else {
                data = dataList.get(ThreadLocalRandom.current().nextInt(size));
                log.debug("using random data: {}", data);
            }
        }
        return data;
    }

}
