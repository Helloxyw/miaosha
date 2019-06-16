package com.xyw.controller;

import com.xyw.rabbitmq.MQSender;
import com.xyw.redis.RedisService;
import com.xyw.service.GoodsService;
import com.xyw.service.MiaoshaService;
import com.xyw.service.MiaoshaUserService;
import com.xyw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/6/3
 * Time:22:03
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender sender;

    private HashMap<Long,Boolean> localOverMap = new HashMap<Long, Boolean>();




}
