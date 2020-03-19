package com.neeson.sms.client;

import com.neeson.common.api.BaseResponse;
import com.neeson.common.auth.AuthConstant;
import com.neeson.sms.common.SmsConstant;
import com.neeson.sms.request.SmsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

/**
 * Create on 2020-03-19
 *
 * @author Administrator
 */

@FeignClient(name = SmsConstant.SERVICE_NAME, path = "/v1", url = "${staffjoy.sms-service-endpoint}")
public interface SmsClient {
    @PostMapping(path = "/queue_send")
    BaseResponse send(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestBody @Valid SmsRequest smsRequest);
}

