package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smart.entities.user;

public interface userrepository extends JpaRepository<user, Integer> {
    
    @Query("select u from user u where u.email = :email")
    user findByEmail(String email); // Correct method for querying user by email
}
