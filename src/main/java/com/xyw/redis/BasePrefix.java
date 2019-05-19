package com.xyw.redis;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:18:29
 */
public abstract class BasePrefix implements KeyPrefix{


    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = this.getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
