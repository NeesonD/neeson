package com.neeson.oss.client.controller;

import com.neeson.oss.IOssRpcService;
import com.neeson.oss.request.OssFileDeleteRequest;
import com.neeson.oss.request.OssTokenRequest;
import com.neeson.oss.response.OssFileDeleteResponse;
import com.neeson.oss.response.OssTokenResponse;
import com.neeson.rpc.client.RpcProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create on 2020-04-03
 *
 * @author Administrator
 */
@RestController
@RequestMapping("OssClientController")
public class OssClientController {

    @Autowired
    private RpcProxy proxy;

    @GetMapping("getOssToken")
    public OssTokenResponse getOssToken() {
        IOssRpcService IOssRpcService = proxy.create(IOssRpcService.class);
        return IOssRpcService.getOssToken(OssTokenRequest.of("1"));
    }

    @GetMapping("deleteOssFile")
    public OssFileDeleteResponse deleteOssFile() {
        IOssRpcService IOssRpcService = proxy.create(IOssRpcService.class);
        return IOssRpcService.deleteOssFile(OssFileDeleteRequest.of("1"));
    }

}
