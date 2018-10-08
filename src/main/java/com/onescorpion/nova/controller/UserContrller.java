package com.onescorpion.nova.controller;

import com.onescorpion.nova.pojo.User;
import com.onescorpion.nova.result.Result;
import com.onescorpion.nova.result.ResultFactory;
import com.onescorpion.nova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserContrller {
    @Resource(name="userServiceImpl")
    private UserService userService;

    @GetMapping(value="/findUserbyId")
    public Result findUserbyId(@RequestParam(value="id")String id){
        return ResultFactory.success(userService.findUserById(id));
    }

    @GetMapping(value="/findUserList")
    public Result findUserList(){
        return ResultFactory.success(userService.findUserList());
    }

    @PostMapping(value="/addUser")
    public Result addUser(@RequestBody User user){
        return ResultFactory.success(userService.addUser(user));
    }

    @PostMapping(value="/login")
    public Result login(@RequestBody User user){
        return ResultFactory.success(userService.login(user));
    }
}
