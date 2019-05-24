package com.xyw.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/21
 * Time:22:41
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");


    /**
     * 检验是否是手机号
     * @param src
     * @return
     */
    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }

        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
}
