package com.gmail.artemkrotenok.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.gmail.artemkrotenok.repository.NewsRepository;
import com.gmail.artemkrotenok.repository.model.News;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.repository.model.UserInformation;
import com.gmail.artemkrotenok.service.impl.NewsServiceImpl;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import com.gmail.artemkrotenok.service.util.NewsConverterUtil;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    public static final long TEST_NEWS_ID = 2L;
    public static final Boolean TEST_NEWS_DELETED_STATUS = false;
    private static final String TEST_NEWS_CONTENT = "Test content";

    @Mock
    private NewsRepository newsRepository;

    private NewsService newsService;

    @BeforeEach
    public void setup() {
        this.newsService = new NewsServiceImpl(newsRepository);
    }

    @Test
    public void add_returnNewsDTO() {
        NewsDTO newsDTO = getValidNewsDTO();
        doNothing().when(newsRepository).persist(any(News.class));
        NewsDTO addedNewsDTO = newsService.add(newsDTO);
        Assertions.assertThat(addedNewsDTO).isNotNull();
        Assertions.assertThat(addedNewsDTO.getDescription()).isEqualTo(newsDTO.getDescription());
        verify(newsRepository, times(1)).persist(any(News.class));
    }

    @Test
    public void getNewsById_returnNewsDTO() {
        News news = getEmptyNews();
        when(newsRepository.findById(TEST_NEWS_ID)).thenReturn(news);
        NewsDTO newsDTO = newsService.findById(TEST_NEWS_ID);
        verify(newsRepository, times(1)).findById(TEST_NEWS_ID);
        Assertions.assertThat(newsDTO).isNotNull();
    }

    @Test
    public void getNewsById_returnNull() {
        when(newsRepository.findById(TEST_NEWS_ID)).thenReturn(null);
        NewsDTO newsDTO = newsService.findById(TEST_NEWS_ID);
        verify(newsRepository, times(1)).findById(TEST_NEWS_ID);
        Assertions.assertThat(newsDTO).isNull();
    }

    private News getEmptyNews() {
        News news = new News();
        news.setDate(LocalDate.now());
        news.setContent(TEST_NEWS_CONTENT);
        news.setComments(new ArrayList<>());
        User user = new User();
        user.setUserInformation(new UserInformation());
        news.setUser(user);
        return news;
    }

    private News getValidNews() {
        News news = getEmptyNews();
        news.setId(TEST_NEWS_ID);
        news.setContent(TEST_NEWS_CONTENT);
        news.setDeleted(TEST_NEWS_DELETED_STATUS);
        return news;
    }

    private NewsDTO getValidNewsDTO() {
        return NewsConverterUtil.getDTOFromObject(getValidNews());
    }

}
