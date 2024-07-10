package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.booking.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findById(int id);

}
