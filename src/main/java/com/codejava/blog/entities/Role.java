package com.codejava.blog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

	
	@Id
	private Integer id;
	private String name;
	
	
}
