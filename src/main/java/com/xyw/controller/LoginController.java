package com.xyw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:16:03
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }



}
