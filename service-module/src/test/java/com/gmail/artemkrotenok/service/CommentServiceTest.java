package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.repository.CommentRepository;
import com.gmail.artemkrotenok.repository.model.Comment;
import com.gmail.artemkrotenok.service.impl.CommentServiceImpl;
import com.gmail.artemkrotenok.service.model.CommentDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    public void setup() {
        this.commentService = new CommentServiceImpl(commentRepository);
    }

    @Test
    public void add_returnCommentDTO() {
        CommentDTO commentDTO = getEmptyCommentDTO();
        doNothing().when(commentRepository).persist(any(Comment.class));
        CommentDTO addedCommentDTO = commentService.add(commentDTO);
        Assertions.assertThat(addedCommentDTO).isNotNull();
        Assertions.assertThat(addedCommentDTO.getContent()).isEqualTo(commentDTO.getContent());
        verify(commentRepository, times(1)).persist(any(Comment.class));
    }

    private CommentDTO getEmptyCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setUserDTO(new UserDTO());
        return commentDTO;
    }

}
