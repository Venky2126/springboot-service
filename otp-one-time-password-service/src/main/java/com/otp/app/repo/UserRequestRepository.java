package com.otp.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otp.app.model.UserRequest;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {
	UserRequest findByUsername(String username);
	
}
