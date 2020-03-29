package com.neeson.zk;

import com.neeson.lock.api.aop.DistributedLockAspect;
import com.neeson.zk.service.ZkDistributedLock;
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
@Import({ZkDistributedLock.class})
@EnableConfigurationProperties(value = CuratorProperties.class)
public class CuratorAutoConfiguration {


    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework curatorFramework(CuratorProperties properties) {
        return CuratorFrameworkFactory.builder()
                .connectString(properties.getConnectString())
                .sessionTimeoutMs(properties.getSessionTimeoutMs())
                .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                .retryPolicy(new RetryNTimes(properties.getRetryCount(), properties.getElapsedTimeMs()))
                .build();
    }

    @Configuration
    @Import(DistributedLockAspect.class)
    public static class AopConfiguration {
    }


}
