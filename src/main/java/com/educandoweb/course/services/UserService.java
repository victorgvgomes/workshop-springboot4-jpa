package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	
	private final UserRepository repository;


	UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	
	public List<User> findAll(){
		return repository.findAll();
		
		
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		
		return obj.orElseThrow(()-> new ResourceNotFoundException(id));

		
	}
	
}
