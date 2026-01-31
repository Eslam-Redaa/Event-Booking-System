package com.demo.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.Entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity , Long> {

	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByUserId(String userId);
	
	boolean existsByEmail(String email);
	
	boolean existsByUserId(String userId);
}

