package com.francinjr.xpenses.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francinjr.xpenses.domain.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserName(String userName);
}
