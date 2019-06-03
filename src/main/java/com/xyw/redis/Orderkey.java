package com.xyw.redis;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:18:37
 */
public class Orderkey extends BasePrefix {
    public Orderkey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public Orderkey(String prefix) {
        super(prefix);
    }

    public static Orderkey getMiaoshaOrderByUidGid = new Orderkey("mouq");
}
