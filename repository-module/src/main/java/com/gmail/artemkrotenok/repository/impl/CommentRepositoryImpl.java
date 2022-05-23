package com.gmail.artemkrotenok.repository.impl;

import com.gmail.artemkrotenok.repository.CommentRepository;
import com.gmail.artemkrotenok.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {
}
