package com.ukayunnuo.bloomfilter.core;

import com.google.common.hash.Funnel;

/**
 * 布隆过滤器 使用
 *
 * @author yunnuo
 * @since 1.0.0
 */
public class BloomFilterHelper<T> {

    private int numHashFunctions;

    private int bitSize;

    private Funnel<T> funnel;


}
