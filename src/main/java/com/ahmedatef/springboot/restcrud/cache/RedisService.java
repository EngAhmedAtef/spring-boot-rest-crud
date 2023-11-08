package com.ahmedatef.springboot.restcrud.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate redisTemplate;

    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T extends Object> void cacheData(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T extends Object> void cacheData(String key, T value, long expiration) {
        cacheData(key, value);
        redisTemplate.expire(key, expiration, TimeUnit.MINUTES);
    }

    public <T extends Object> Optional<T> getData(String key) {
        Object data = redisTemplate.opsForValue().get(key);
        if (data == null)
            return Optional.empty();
        return Optional.of((T) data);
    }

    public <T extends Object> void pushToList(String listName, T value) {
        redisTemplate.opsForList().rightPush(listName, value);
    }

    public <T extends Object> Optional<T> popFromList(String listName, boolean shouldRemove) {
        if (!redisTemplate.hasKey(listName))
            return Optional.empty();
        T value = (T) (shouldRemove ? redisTemplate.opsForList().leftPop(listName) : redisTemplate.opsForList().range(listName, 0, 0).get(0));
        return Optional.of(value);
    }

    public <T extends Object> Optional<List<T>> popList(String listName) {
        return Optional.of(redisTemplate.opsForList().range(listName, 0, -1));
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    public void flushAll() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().serverCommands().flushAll();
    }
}