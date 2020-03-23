package com.neeson.account.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/22 18:19
 */
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean lock(String key, String value, long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.set(key.getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8")),
                        Expiration.from(timeout, timeUnit), RedisStringCommands.SetOption.SET_IF_ABSENT));
    }

    public boolean unlock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        return stringRedisTemplate.execute((RedisCallback<Boolean>)connection ->
                connection.eval(script.getBytes(), ReturnType.BOOLEAN, 1,
                        key.getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8"))));
    }

}
