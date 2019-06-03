package com.xyw.rabbitmq;

import com.xyw.domain.MiaoshaUser;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/30
 * Time:21:26
 */
public class MiaoshaMessage {

    private MiaoshaUser user;
    private long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
