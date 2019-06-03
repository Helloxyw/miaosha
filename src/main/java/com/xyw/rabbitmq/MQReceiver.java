package com.xyw.rabbitmq;

import com.xyw.domain.MiaoshaOrder;
import com.xyw.domain.MiaoshaUser;
import com.xyw.redis.RedisService;
import com.xyw.service.GoodsService;
import com.xyw.service.OrderService;
import com.xyw.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/30
 * Time:21:27
 */
@Service
public class MQReceiver {

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    /**
     *
     * @param message
     */
    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE )
    public void receive(String message){

        logger.info("receive message :" + message);

        MiaoshaMessage mm = RedisService.stringToBean(message,MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = user.getId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }

        //减库存 下订单 写入秒杀订单







    }
}
