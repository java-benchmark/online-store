package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.service.model.CommentDTO;

public interface CommentService {

    CommentDTO add(CommentDTO commentDTO);

    boolean deleteById(Long id);

}
