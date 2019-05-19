package com.xyw.redis;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:18:44
 */
public class Userkey extends BasePrefix {

    public Userkey(String prefix) {
        super(prefix);
    }

    public static Userkey getById = new Userkey("id");

    public static Userkey getByName = new Userkey("name");
}
