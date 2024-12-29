package com.blog.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
