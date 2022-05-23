package com.gmail.artemkrotenok.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gmail.artemkrotenok.repository.model.Comment;
import com.gmail.artemkrotenok.repository.model.News;
import com.gmail.artemkrotenok.service.model.CommentDTO;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;

public class NewsConverterUtil {

    public static final int MAX_LENGTH_DESCRIPTION_NEWS = 200;
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    public static NewsDTO getDTOFromObject(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setDate(news.getDate().toString());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setDescription(getDescription(news));
        newsDTO.setContent(news.getContent());
        List<CommentDTO> commentsDTO = getCommentsDTOFromObject(news.getComments());
        newsDTO.setCommentsDTO(commentsDTO);
        UserDTO userDTO = UserConverterUtil.getDTOFromObject(news.getUser());
        newsDTO.setUserDTO(userDTO);
        return newsDTO;
    }

    public static News getObjectFromDTO(NewsDTO newsDTO) {
        News news = new News();
        String stringDate = newsDTO.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDate date = LocalDate.parse(stringDate, formatter);
        news.setDate(date);
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setUser(UserConverterUtil.getObjectFromDTO(newsDTO.getUserDTO()));
        return news;
    }

    public static List<CommentDTO> getCommentsDTOFromObject(List<Comment> comments) {
        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            commentsDTO.add(CommentConverterUtil.getDTOFromObject(comment));
        }
        return commentsDTO;
    }

    private static String getDescription(News news) {
        String content = news.getContent();
        String descriptionContent;
        if (content.length() <= MAX_LENGTH_DESCRIPTION_NEWS) {
            return content;
        }
        descriptionContent = content.substring(0, MAX_LENGTH_DESCRIPTION_NEWS - 1);
        int lengthDescription = descriptionContent.lastIndexOf(' ');
        descriptionContent = descriptionContent.substring(0, lengthDescription);
        return descriptionContent;
    }

}
