package com.nvs.th_java.service;

import com.nvs.th_java.model.User;

public interface UserService {
    User findByUsername(String username);

    void setDefaultRole(String username);

    void save(User user);
}
