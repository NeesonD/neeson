package com.neeson.rpc.common;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/2 22:17
 */
public interface Constant {

    int ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data/";

}
