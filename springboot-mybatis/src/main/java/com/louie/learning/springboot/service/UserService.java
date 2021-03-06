package com.louie.learning.springboot.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.louie.learning.springboot.dao.UserDao;
import com.louie.learning.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserDao userDao;

    public int deleteByPrimaryKey(String id) {
        return userDao.deleteByPrimaryKey(id);
    }

    public int insert(User record) {
        return userDao.insert(record);
    }

    public int insertSelective(User record) {
        return userDao.insertSelective(record);
    }

    public User selectByPrimaryKey(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(User record) {
        return userDao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKey(record);
    }

    public List<User> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        return userDao.findAll();
    }

    public Page<User> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userDao.findByPage();
    }
}
