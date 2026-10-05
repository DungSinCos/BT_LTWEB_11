package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.User_24133010;

public interface UserDAO_24133010 {
    User_24133010 findByEmail(String email);
    User_24133010 findById(int id);
    void save(User_24133010 user);
    void update(User_24133010 user);
    boolean existsByEmail(String email);
}
