package com.xyw.dao;

import com.xyw.domain.MiaoshaUser;
import com.xyw.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

//    int reduceStock(MiaoshaUser)
}
