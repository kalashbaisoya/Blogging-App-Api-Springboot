package com.codejava.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codejava.blog.entities.Comment;
import com.codejava.blog.entities.Post;
import com.codejava.blog.entities.User;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	List<Comment> findByUser(User user);
	List<Comment> findByPost(Post post);
}
