package com.otp.app.service;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.otp.app.model.UserRequest;
import com.otp.app.repo.UserRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

	private final UserRequestRepository userRequestRepository;

	// This method is used by Spring Security
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserRequest userRequest = userRequestRepository.findByUsername(username);

		if (userRequest == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		GrantedAuthority authority = new SimpleGrantedAuthority(userRequest.getRole());
		return new User(userRequest.getUsername(), userRequest.getPassword(), Arrays.asList(authority));
	}

	// This method is used to save the user details
	public UserRequest saveUser(UserRequest userRequest) {
		// convert the password to encrypted format using BCryptPasswordEncoder and save
		// it
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
		userRequest.setPassword(hashedPassword);
		log.info("UserRequest : {}", userRequest);
		return userRequestRepository.save(userRequest);
	}

	// Fetch user by USERNAME to map email to OTP and send the email
	public UserRequest getUser(String username) {
		log.info("Username : {}", username);
		UserRequest userRequest = userRequestRepository.findByUsername(username);
		if (userRequest != null) {
			log.info("UserRequest : {}", userRequest);
			return userRequest;
		} else {
			log.info("User not found with username: {}", username);
			return null;
		}

	}

}
