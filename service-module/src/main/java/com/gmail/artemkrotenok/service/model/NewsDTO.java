package com.gmail.artemkrotenok.service.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewsDTO {

    private Long id;
    @NotNull(message = "The field 'date' must be filled")
    private String date;
    @NotNull(message = "The field 'title' must be filled")
    @Size(min = 1, max = 100, message = "'title' size must be between 1 and 100 characters")
    private String title;
    private String description;
    @NotNull(message = "The field 'content' must be filled")
    @Size(min = 1, max = 1000, message = "'content' size must be between 1 and 1000 characters")
    private String content;
    private UserDTO userDTO;
    private Boolean isDeleted;
    private List<CommentDTO> commentsDTO = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public List<CommentDTO> getCommentsDTO() {
        return commentsDTO;
    }

    public void setCommentsDTO(List<CommentDTO> commentsDTO) {
        this.commentsDTO = commentsDTO;
    }

}
