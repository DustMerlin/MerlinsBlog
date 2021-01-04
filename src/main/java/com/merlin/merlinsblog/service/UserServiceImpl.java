package com.merlin.merlinsblog.service;

import com.merlin.merlinsblog.dao.UserRepository;
import com.merlin.merlinsblog.po.User;
import com.merlin.merlinsblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User chechUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
