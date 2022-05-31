package com.lrng.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrng.blog.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

}
