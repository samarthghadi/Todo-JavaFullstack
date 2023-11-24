package com.in28minutes.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	// LDAP or Database
	// In memory

	// InMemoryUserDetailsManager
	// InMemoryUserDetailsManager(UserDetails... users)

	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		
		UserDetails userDetails = createNewUser("Samarth", "abc");
		UserDetails userDetails1 = createNewUser("Adam", "adam");
		UserDetails userDetails2 = createNewUser("Kate", "kate");

		return new InMemoryUserDetailsManager(userDetails1, userDetails2, userDetails);
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder
				= input -> passwordEncoder().encode(input);
		
		UserDetails userDetails = User.builder()
									  .passwordEncoder(passwordEncoder)
									  .username(username)
									  .password(password)
									  .roles("USER", "ADMIN").build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.formLogin(withDefaults());
		return http.build();
	}
}
