package com.example.socialpost.app.service;

import com.example.socialpost.app.model.User;

public interface UserService {

	boolean usernameExists(String username);
    boolean emailExists(String email);
    User registerUser(User user);
    User saveUser(User user);

}