package com.neeson.common.api;

import lombok.Builder;

/**
 * Create on 2020-03-19
 *
 * @author Administrator
 */
public class BaseResponse {

    private String message;
    @Builder.Default
    private ResultCode code = ResultCode.SUCCESS;

    public boolean isSuccess() {
        return code == ResultCode.SUCCESS;
    }

}
