package com.onescorpion.nova.mapper;

import com.onescorpion.nova.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findUserList();

    int login(User user);
}