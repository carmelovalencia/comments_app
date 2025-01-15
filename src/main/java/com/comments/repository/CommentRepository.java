package com.comments.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.comments.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
