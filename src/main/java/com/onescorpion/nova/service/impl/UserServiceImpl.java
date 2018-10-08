package com.onescorpion.nova.service.impl;

import com.onescorpion.nova.mapper.UserMapper;
import com.onescorpion.nova.pojo.User;
import com.onescorpion.nova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findUserList(){
        return userMapper.findUserList();
    }

    @Override
    public User findUserById(String id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean addUser(User user){
        return userMapper.insert(user)>0;
    }

    @Override
    public Boolean login(User user){
        return userMapper.login(user)>0;
    }
}
