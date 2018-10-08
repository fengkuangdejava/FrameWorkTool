package com.onescorpion.nova.service;

import com.onescorpion.nova.pojo.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {

  List<User> findUserList();

  User findUserById(String id);

  Boolean addUser(User user);

  Boolean login(User user);

}
