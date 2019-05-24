package com.xyw.controller;

import com.xyw.domain.MiaoshaUser;
import com.xyw.redis.GoodsKey;
import com.xyw.redis.RedisService;
import com.xyw.service.GoodsService;
import com.xyw.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/23
 * Time:00:06
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;


    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 商品列表
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){
        model.addAttribute("user", user);

        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);

        SpringWebContext ctx = new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap(),applicationContext);

        //手动渲染
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if(!StringUtils.isEmpty(html)){
            //页面缓存
            redisService.set(GoodsKey.getGoodsList,"",html);
        }

        return html;


    }
}
