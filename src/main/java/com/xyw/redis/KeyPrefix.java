package com.xyw.redis;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:18:26
 * 键名前缀
 */
public interface KeyPrefix {

    /**
     * 过期时间
     * @return
     */
    int expireSeconds();

    /**
     * 获取前缀
     * @return
     */
    String getPrefix();
}
