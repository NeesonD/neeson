package com.neeson.rpc.support;

import com.neeson.rpc.client.RpcProxy;
import org.springframework.beans.factory.Aware;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/4 14:15
 */
public interface RpcProxyAware extends Aware {

    /**
     * 设置 PpcProxy
     * @param rpcProxy
     */
    void setRpcProxy(RpcProxy rpcProxy);

}
