package top.doperj.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = "top.doperj")
public class RedisUtil<T> {
    private RedisTemplate<String, T> redisTemplate;
    private HashOperations<String, T, T> hashOperation;
    private ListOperations<String, T> listOperation;
    private ValueOperations<String, T> valueOperations;

    @Autowired
    RedisUtil(RedisTemplate<String, T> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperation = redisTemplate.opsForHash();
        this.listOperation = redisTemplate.opsForList();
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void putMap(String redisKey, T key, T data) {
        hashOperation.put(redisKey, key, data);
    }

    public void removeMap(String redisKey, T key) {
        hashOperation.delete(redisKey, key);
    }

    public T getMapAsSingleEntry(String redisKey,T key) {
        return hashOperation.get(redisKey,key);
    }

    public Map<T, T> getMapAsAll(String redisKey) {
        return hashOperation.entries(redisKey);
    }

    public void putValue(String key, T value) {
        valueOperations.set(key, value);
    }

    public void putValueWithExpireTime(String key, T value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }

    public T getValue(String key) {
        return valueOperations.get(key);
    }

    public void setExpire(String key,long timeout,TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

}

