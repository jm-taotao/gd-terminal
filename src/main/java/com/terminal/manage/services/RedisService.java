package com.terminal.manage.services;

/**
 * @author TAO
 * @date 2022/7/9 / 16:40
 */
public interface RedisService<K,V> {

    V get(K key);

    void set(K key, V value);

    void set(K key, V value, Long timeOut);

}
