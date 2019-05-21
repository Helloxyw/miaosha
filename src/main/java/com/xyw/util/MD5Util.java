package com.xyw.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/20
 * Time:00:12
 */
public class MD5Util {

    public static final String SALT = "1a2b3c4d";


    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    /**
     * 输入密码到进入表单层加密
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass){
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 表单层到数据库层加密
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDbPass(String formPass,String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2)+ formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }


    /**
     * 用户输入到密码加密成存入数据库到明文密码
     * @param inputPass
     * @param saltDB
     * @return
     */
    public static String inputPassToDbPass(String inputPass,String saltDB){
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDbPass(formPass,saltDB);
        return dbPass;
    }
}
