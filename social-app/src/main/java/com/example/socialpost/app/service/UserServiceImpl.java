package com.example.socialpost.app.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.socialpost.app.model.User;
import com.example.socialpost.app.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        // Encode password before saving
      //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}