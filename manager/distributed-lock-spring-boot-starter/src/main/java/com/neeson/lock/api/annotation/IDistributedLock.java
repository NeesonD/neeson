package com.neeson.lock.api.annotation;

import com.neeson.lock.api.LockInfo;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/28 20:08
 */
public interface IDistributedLock {

    void lock(LockInfo lockInfo);

    boolean tryLock(LockInfo lockInfo);

    void unLock(LockInfo lockInfo);

}
