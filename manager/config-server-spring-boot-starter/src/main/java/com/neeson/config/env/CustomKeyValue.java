package com.neeson.config.env;

import java.util.Map;

/**
 * @author neeson
 */
public class CustomKeyValue {

    private Map<String, Object> keyValue;

    public CustomKeyValue(Map<String, Object> keyValue) {
        this.keyValue = keyValue;
    }

    public Object get(String key) {
        return keyValue.get(key);
    }

}
