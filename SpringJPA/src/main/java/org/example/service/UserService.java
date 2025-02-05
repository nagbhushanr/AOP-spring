package org.example.service;

import java.util.List;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public void addUser(String name,String email){
		User user = new User(name,email);
		userRepository.saveUser(user);
	}

	public List<User> getUsers(){
		return userRepository.getAllUsers();
	}
}
