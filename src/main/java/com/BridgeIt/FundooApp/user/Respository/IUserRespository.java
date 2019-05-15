package com.BridgeIt.FundooApp.user.Respository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.BridgeIt.FundooApp.user.Model.User;

public interface IUserRespository extends  MongoRepository<User, String>{
	Optional<User> findByEmailId(String  email);
	Optional<User>findByUserId(String id);

}

