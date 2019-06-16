package com.xyw.access;

import com.xyw.domain.MiaoshaUser;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/6/16
 * Time:23:32
 */
public class UserContext {

    //线程本地
    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<>();

    public static void setUser(MiaoshaUser user){
        userHolder.set(user);
    }

    public static MiaoshaUser get(){
        return userHolder.get();
    }


}
