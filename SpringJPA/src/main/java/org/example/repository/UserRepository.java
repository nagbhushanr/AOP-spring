package org.example.repository;

import java.util.List;

import org.example.configuration.MongoConfig;
import org.example.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component("userRepository")
public class UserRepository {

	MongoConfig mongoConfig;

	MongoTemplate mongoTemplate;

	public UserRepository(MongoConfig mongoConfig){
		this.mongoConfig = mongoConfig;
		this.mongoTemplate = this.mongoConfig.mongoTemplate(this.mongoConfig.mongoClient());
	}

	public void saveUser(User user){
		mongoTemplate.save(user);
	}

	public List<User> getAllUsers(){
		return mongoTemplate.findAll(User.class);
	}
}
