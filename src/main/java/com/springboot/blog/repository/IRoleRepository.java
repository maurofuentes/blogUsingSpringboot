package com.springboot.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.model.Role;

public interface IRoleRepository extends JpaRepository<Role,Long>{

    public Optional<Role> findByName(String name);
    
}
