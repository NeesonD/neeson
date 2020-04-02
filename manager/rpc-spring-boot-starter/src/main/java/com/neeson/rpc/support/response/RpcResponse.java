package com.neeson.rpc.support.response;

import lombok.Data;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/2 22:35
 */
@Data
public class RpcResponse {
    private String requestId;
    private Throwable error;
    private Object result;

    public boolean isError() {
        return false;
    }
}
