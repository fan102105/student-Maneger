package com.itheima.service;

import com.itheima.domain.User;

import java.io.IOException;

public interface IUserService {
    User login(User user) throws IOException;
}
