package com.bosch.stocktoship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bosch.stocktoship.entity.User;


public interface UserRepository extends JpaRepository<User,String> {
	// default functions are included

	    // jpql
	    @Query("SELECT u FROM User u WHERE u.username = :username")
	    User findByUsername(@Param("username") String username);
	   
	}
