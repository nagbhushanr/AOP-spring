package org.example.service;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Objects;

import org.example.customAnnotation.RetryDBOperation;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

	@RetryDBOperation
	public User getUserByName(String name) throws Exception{
		User user = userRepository.getUserByName(name);
		if(Objects.isNull(user)){
			throw new NotFoundException();
		}
		return user;
	}
}
