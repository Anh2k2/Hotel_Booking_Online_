package com.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.booking.repository.UserRepository;
import com.booking.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> GetAllUser(){
		return userRepository.findAll();
	}

	@Modifying
	public User SaveUser(User user) {
		User save = userRepository.save(user);
		return save;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public long UserCount() {
		return userRepository.count();
	}

	public User findById(int id){
		return userRepository.findById(id);
	}

}
