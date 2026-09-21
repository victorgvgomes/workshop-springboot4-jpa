package com.educandoweb.course.services;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	
	private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


	UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
        this.passwordEncoder = passwordEncoder;

	}
	
	
	public Page<User> findAll(Pageable pageable) {
	    return repository.findAll(pageable);  // busca só UMA FATIA
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		
		return obj.orElseThrow(()-> new ResourceNotFoundException(id));

		
	}
	
	public User insert(User obj) {
		 obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		return repository.save(obj);
	}
	
	public void delete (Long id) {
		
		try {
			repository.deleteById(id);
			
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
			
		}
	}
	
	public User update(Long id, User obj) {
		
		try {
		User entity = repository.getReferenceById(id);
		updateData(entity, obj);
		return repository.save(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);

		}
		
	}


	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		
	}
	
	
}
