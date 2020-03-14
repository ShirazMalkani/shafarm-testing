package com.raqami.shafarm.controller;

import com.raqami.shafarm.model.User;
import com.raqami.shafarm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path="/user", produces = "application/json")
    public User getUser(@RequestParam(value = "id", defaultValue = "123") String id)
    {
        return userService.getUser(Integer.parseInt(id));
    }
}
