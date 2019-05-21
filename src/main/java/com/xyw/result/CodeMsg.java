package com.xyw.result;

import com.sun.tools.javac.jvm.Code;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:15:03
 * 错误信息
 */
public class CodeMsg {
    private int code;
    private String msg;

    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");


    //登录模块 5002XX
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214,"手机号不存在");


    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215,"密码错误");

    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX




    private CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
