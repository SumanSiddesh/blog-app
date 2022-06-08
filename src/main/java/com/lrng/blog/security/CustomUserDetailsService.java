package com.lrng.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.User;
import com.lrng.blog.exceptions.ResourceNotFoundException;
import com.lrng.blog.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// loading user from database
		User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email", username));

		return user;
	}

}
