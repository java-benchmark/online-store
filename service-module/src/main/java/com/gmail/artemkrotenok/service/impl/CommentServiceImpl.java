package com.gmail.artemkrotenok.service.impl;

import com.gmail.artemkrotenok.repository.CommentRepository;
import com.gmail.artemkrotenok.repository.model.Comment;
import com.gmail.artemkrotenok.service.CommentService;
import com.gmail.artemkrotenok.service.model.CommentDTO;
import com.gmail.artemkrotenok.service.util.CommentConverterUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public CommentDTO add(CommentDTO commentDTO) {
        Comment comment = getObjectFromDTO(commentDTO);
        commentRepository.persist(comment);
        return getDTOFromObject(comment);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Comment comment = commentRepository.findById(id);
        commentRepository.remove(comment);
        return true;
    }

    private CommentDTO getDTOFromObject(Comment comment) {

        return CommentConverterUtil.getDTOFromObject(comment);
    }

    private Comment getObjectFromDTO(CommentDTO commentDTO) {

        return CommentConverterUtil.getObjectFromDTO(commentDTO);
    }

}
