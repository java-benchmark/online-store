package com.gmail.artemkrotenok.service.util;

import com.gmail.artemkrotenok.repository.model.Comment;
import com.gmail.artemkrotenok.service.model.CommentDTO;

public class CommentConverterUtil {

    public static CommentDTO getDTOFromObject(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getDate());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserDTO(UserConverterUtil.getDTOFromObject(comment.getUser()));
        commentDTO.setNewsId(comment.getNewsId());
        return commentDTO;
    }

    public static Comment getObjectFromDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDate(commentDTO.getDate());
        comment.setContent(commentDTO.getContent());
        comment.setUser(UserConverterUtil.getObjectFromDTO(commentDTO.getUserDTO()));
        comment.setNewsId(commentDTO.getNewsId());
        return comment;
    }

}
