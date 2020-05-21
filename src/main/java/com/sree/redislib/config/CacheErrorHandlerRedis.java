package com.sree.redislib.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CacheErrorHandlerRedis implements CacheErrorHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CacheErrorHandlerRedis.class);
    private static final String CACHE_ERROR_MSG = "Failed to connect to redis, ignoring cache";

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        LOG.warn(CACHE_ERROR_MSG, exception);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        LOG.warn(CACHE_ERROR_MSG, exception);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        LOG.warn(CACHE_ERROR_MSG, exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        LOG.warn(CACHE_ERROR_MSG, exception);
    }
}