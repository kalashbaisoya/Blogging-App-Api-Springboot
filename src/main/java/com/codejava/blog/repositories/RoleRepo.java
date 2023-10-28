package com.codejava.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codejava.blog.entities.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

}