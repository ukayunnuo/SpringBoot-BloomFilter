package com.ukayunnuo.bloomfilter.core;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redisson 布隆过滤器
 *
 * @author yunnuo
 * @since 1.0.0
 */
@Component
public class RedissonBloomFilter {

    @Resource
    private RedissonClient redissonClient;


    public <T> RBloomFilter<T> create(String filterName, long capacity, double errorRate){
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(capacity, errorRate);
        return bloomFilter;
    }

}
