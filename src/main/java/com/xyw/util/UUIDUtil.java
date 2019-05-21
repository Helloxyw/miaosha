package com.xyw.util;

import java.util.UUID;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/21
 * Time:22:04
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
