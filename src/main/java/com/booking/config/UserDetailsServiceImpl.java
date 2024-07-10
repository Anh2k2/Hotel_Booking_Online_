package com.booking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.booking.model.User;
import com.booking.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		User user = userService.findUserByEmail(username);
		
		System.out.println("User found !!! _> "+user);

		if (user == null) {
			throw new UsernameNotFoundException("could not found User !!");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		return customUserDetails;
	}

}
