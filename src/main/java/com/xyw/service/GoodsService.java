package com.xyw.service;

import com.xyw.dao.GoodsDao;
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


    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }
}
