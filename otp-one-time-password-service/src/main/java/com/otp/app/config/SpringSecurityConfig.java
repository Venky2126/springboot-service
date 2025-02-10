package com.otp.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.otp.app.service.UserService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@NoArgsConstructor
class SpringSecurityConfig {

	private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private UserService userService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("Configuring Spring Security");
		http.csrf(csrf -> csrf.disable()) // use lambda DSL to disable CSRF
				// v1 configuration
	/**	
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/v1", "/v1/aboutus", "/v1/signup")
						.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**")
						.hasRole("USER").anyRequest().authenticated())
				        .formLogin(formLogin -> formLogin.loginPage("/v1/login").defaultSuccessUrl("/v1/dashboard", true)
						.failureUrl("/v1/login?error=true").permitAll())
	**/
				// template configuration
				
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/", "/aboutus","/register").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated())
                        .formLogin(formLogin -> formLogin.loginPage("/loginTemplate").defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true").permitAll())
                        
                        
                .logout(logout -> logout.permitAll())
				.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler));
		return http.build();
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Configuring Global Authentication");
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
}