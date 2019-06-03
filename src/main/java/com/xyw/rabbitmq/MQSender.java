package com.xyw.rabbitmq;

import com.xyw.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/30
 * Time:21:30
 */
@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     * @param message
     */
    public void sendMiaoshaMessage(MiaoshaMessage message){
        String msg = RedisService.beanToString(message);
        logger.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
    }


}
