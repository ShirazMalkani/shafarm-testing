package com.raqami.shafarm.service;

import com.raqami.shafarm.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUser(int userId) {
        User tempUser = new User();
        tempUser.setName("Ali");
        tempUser.setEmail("temporary@temp.com");
        return tempUser;
    }
}
