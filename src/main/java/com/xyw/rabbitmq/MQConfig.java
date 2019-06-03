package com.xyw.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/30
 * Time:21:22
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";
    public static final String MIAOSHA_QUEUE = "queue";



    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }
}
