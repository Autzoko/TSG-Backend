package com.example.tsgbackend.config.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheConfig {
    private Cache<String, String> cache;

    /**
     *
     * @param key key
     * @param value value
     * @param expireTime expire time
     */
    public void put(String key, String value, int expireTime) {
        cache = CacheBuilder.newBuilder().expireAfterWrite(expireTime, TimeUnit.MINUTES).build();
        cache.put(key, value);
    }

    /**
     *
     * @param key key
     * @return java.lang.String
     */
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     *
     * @param key key
     */
    public void invalidate(String key) {
        cache.invalidate(key);
    }

    /**
     * @description clear expired cache at 1 a.m. per day
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void clearExpired() {
        if(cache != null) {
            cache.cleanUp();
        }
    }
}
