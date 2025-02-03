package com.otp.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otp.app.model.UserPojo;

@Repository
public interface UserRepository extends JpaRepository<UserPojo, String>{
   UserPojo findByUsername(String username);
} 
