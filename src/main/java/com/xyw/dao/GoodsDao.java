package com.xyw.dao;

import com.xyw.domain.MiaoshaGoods;
import com.xyw.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/23
 * Time:00:22
 */
public interface GoodsDao {

    @Select("")
    List<GoodsVo> listGoodsVo();

    @Select("")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);


    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
     int reduceStock(MiaoshaGoods g);

    @Update("update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
     int resetStock(MiaoshaGoods g);
}
