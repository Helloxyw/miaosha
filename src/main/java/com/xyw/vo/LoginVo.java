package com.xyw.vo;

import com.xyw.validator.IsMobile;

import javax.validation.constraints.NotNull;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:16:22
 */
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;
}
