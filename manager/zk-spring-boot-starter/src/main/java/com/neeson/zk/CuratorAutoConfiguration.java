package com.neeson.zk;

import com.neeson.zk.service.DistributedLockByCurator;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/22 18:19
 */
@Configuration
@Import(DistributedLockByCurator.class)
@EnableConfigurationProperties(value = CuratorProperties.class)
public class CuratorAutoConfiguration {


    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework(CuratorProperties properties) {
        return CuratorFrameworkFactory.newClient(
                properties.getConnectString(),
                properties.getSessionTimeoutMs(),
                properties.getConnectionTimeoutMs(),
                new RetryNTimes(properties.getRetryCount(), properties.getElapsedTimeMs()));
    }

}
