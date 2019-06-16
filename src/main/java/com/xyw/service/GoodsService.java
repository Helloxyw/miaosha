package com.xyw.service;

import com.xyw.dao.GoodsDao;
import com.xyw.domain.MiaoshaGoods;
import com.xyw.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/23
 * Time:00:15
 */
@Service
public class GoodsService {


    @Autowired
    private GoodsDao goodsDao;


    /**
     *
     * @return
     */
    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    /**
     * 根据商品id获取商品
     * @param goodsId
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(long goodsId){
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    /**
     *  减少库存
     * @param goods
     * @return
     */
    public boolean reduceStock(GoodsVo goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }

    /**
     *
     * @param goodsList
     */
    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            MiaoshaGoods g = new MiaoshaGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock(g);
        }
    }

}
