package com.neeson.rpc;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/2 22:07
 */
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {

    private String registryAddress;

    private String serverHost;
    private String serverPort;

}
