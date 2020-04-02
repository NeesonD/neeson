package com.neeson.rpc.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.context.annotation.DependsOn;

import java.util.concurrent.CountDownLatch;

import static com.neeson.rpc.common.Constant.ZK_DATA_PATH;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/2 22:12
 */
@Slf4j
public class ServiceRegistry {

    private CountDownLatch latch = new CountDownLatch(1);

    private String registryAddress;

    private CuratorFramework curatorFramework;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void register(String data) {
        if (data != null) {
            try {
                curatorFramework
                        .create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(ZK_DATA_PATH + data);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }
    }



}
