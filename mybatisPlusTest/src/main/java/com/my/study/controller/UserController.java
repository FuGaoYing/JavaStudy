package com.my.study.controller;

import com.my.study.entity.User;
import com.my.study.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.PrintServiceLookup;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FGY
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    @PutMapping("/save")
    public boolean saveUser(@RequestBody User user) {
        log.info("入参: {}", user);
        return userService.save(user);
    }
}
