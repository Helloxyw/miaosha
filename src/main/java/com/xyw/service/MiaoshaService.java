package com.xyw.service;

import com.xyw.domain.MiaoshaOrder;
import com.xyw.domain.MiaoshaUser;
import com.xyw.domain.OrderInfo;
import com.xyw.redis.MiaoshaKey;
import com.xyw.redis.RedisService;
import com.xyw.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/6/3
 * Time:21:56
 */
@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    /**
     * 秒杀操作
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods){
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if(success){
            return orderService.createOrder(user,goods);
        }else {
            setGoodsOver(goods.getId());
            return null;
        }
    }


    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    public long getMiaoshaResult(Long userId,long goodsId){
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }

    /**
     * 设置商品失效
     * @param goodsId
     */
    private void setGoodsOver(Long goodsId){
        redisService.set(MiaoshaKey.isGoodsOver,"" + goodsId,true);
    }


    /**
     * 商品是否失效
     * @param goodsId
     * @return
     */
    private boolean getGoodsOver(long goodsId){
        return redisService.exist(MiaoshaKey.isGoodsOver, ""+goodsId);
    }
}
