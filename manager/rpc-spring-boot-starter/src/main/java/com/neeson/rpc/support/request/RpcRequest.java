package com.neeson.rpc.support.request;

import lombok.Data;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/2 22:34
 */
@Data
public class RpcRequest {

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;


}
