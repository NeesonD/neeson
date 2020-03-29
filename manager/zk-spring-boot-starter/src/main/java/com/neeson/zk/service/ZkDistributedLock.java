package com.neeson.zk.service;

import com.neeson.lock.api.LockInfo;
import com.neeson.lock.api.annotation.IDistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/22 18:19
 */
public class ZkDistributedLock implements IDistributedLock {

    @Autowired
    private CuratorFramework curatorFramework;

    private ThreadLocal<InterProcessMutex> interProcessMutexThreadLocal = new ThreadLocal<>();

    @Override
    public void lock(LockInfo lockInfo) {
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, lockInfo.getKey());
        interProcessMutexThreadLocal.set(mutex);
        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock(LockInfo lockInfo) {
        try {
            curatorFramework
                    .create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(lockInfo.getKey());
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void unLock(LockInfo lockInfo) {
        try {
            interProcessMutexThreadLocal.get().release();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            interProcessMutexThreadLocal.remove();
        }
    }
}
