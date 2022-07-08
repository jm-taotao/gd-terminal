package com.terminal.manage.services;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.model.User;

import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/27
 */
public interface UserService {

    Optional<User> login(String loginName, String password);

    Optional<Boolean> addUser(User user);

    Optional<Boolean> updateUser(User user);

    Optional<Boolean> delUserById(Long userId);

    Optional<PageInfo<User>> pageUserList(Integer page, Integer pageSize, User user);

    Optional<User> getUserById(Long userId);
}