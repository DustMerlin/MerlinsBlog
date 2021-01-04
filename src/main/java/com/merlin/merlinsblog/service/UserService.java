package com.merlin.merlinsblog.service;


import com.merlin.merlinsblog.po.User;

public interface UserService {

    User chechUser(String username,String password);

}
