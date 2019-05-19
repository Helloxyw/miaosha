package com.xyw.service;

import com.xyw.dao.UserDao;
import com.xyw.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:16:17
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }



}
