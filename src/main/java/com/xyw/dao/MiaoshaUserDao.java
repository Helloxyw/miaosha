package com.xyw.dao;

import com.xyw.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/21
 * Time:21:47
 */
public interface MiaoshaUserDao {

    /**
     * 根据id获取秒杀用户
     * @param id
     * @return
     */
    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getById(@Param("id") long id);


    /**
     * 更新密码
     * @param toBeUpdate
     */
    @Update("update miaosha_user set password = #{password} where id = #{id}")
    void update(MiaoshaUser toBeUpdate);
}
