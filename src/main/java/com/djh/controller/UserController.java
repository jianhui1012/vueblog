package com.djh.controller;


import com.djh.common.lang.Result;
import com.djh.entity.User;
import com.djh.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djh
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result getUserById(){
       return Result.result(userService.getById(1));
    }


    @PostMapping("/save")
    public Result saveUser(@Validated @RequestBody User user){
        return Result.result("userService.save(user)");
    }


}
