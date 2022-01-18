package com.example.cacheservice.service.controller;

import com.example.cacheservice.common.GolbalUtil;
import com.example.cacheservice.domain.UserEntity;
import com.example.cacheservice.dto.ResultDTO;
import com.example.cacheservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @author yongqi yang
 * @date 2022/1/18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/get")
    public ResultDTO<Object> getInfo(@RequestParam("username") String username){
        return userService.getInfo(username);
    }

    @GetMapping("/add")
    public ResultDTO<UserEntity> addUser(){
        return userService.addUser();
    }

    @GetMapping("/map")
    public String map(){
        return GolbalUtil.getUserMap().toString();
    }

    @GetMapping("/cache")
    public ResultDTO<Object> cache(String name) throws Throwable {
        return userService.cache(name);
    }

}
