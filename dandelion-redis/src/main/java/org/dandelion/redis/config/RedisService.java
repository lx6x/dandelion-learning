package org.dandelion.redis.config;

import java.util.List;

public interface RedisService {

    /**
     * set值
     *
     * @param key   key
     * @param value value
     */
    void set(String key, Object value);

    /**
     * set值-设置超时时间
     *
     * @param key   key
     * @param value value
     * @param time  超时时间(s-秒)
     */
    void set(String key, Object value, long time);

    /**
     * get值
     */
    Object get(String key);

    /**
     * 根据key删除属性
     *
     * @param key key
     */
    Boolean del(String key);

    /**
     * 批量删除key
     *
     * @param keys key
     */
    Long del(List<String> keys);
}
