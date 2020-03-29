package com.neeson.lock.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/28 20:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockInfo {

    private String key;

    private String value;

}
