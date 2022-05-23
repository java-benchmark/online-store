package com.gmail.artemkrotenok.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.gmail.artemkrotenok.repository.NewsRepository;
import com.gmail.artemkrotenok.repository.model.Comment;
import com.gmail.artemkrotenok.repository.model.News;
import com.gmail.artemkrotenok.service.NewsService;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import com.gmail.artemkrotenok.service.util.NewsConverterUtil;
import com.gmail.artemkrotenok.service.util.PaginationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    @Transactional
    public NewsDTO add(NewsDTO newsDTO) {
        News news = getObjectFromDTO(newsDTO);
        news.setDeleted(false);
        newsRepository.persist(news);
        return getDTOFromObject(news);
    }

    @Override
    @Transactional
    public Long getCountNews() {
        return newsRepository.getCount();
    }

    @Override
    @Transactional
    public List<NewsDTO> getItemsByPageSorted(Integer page) {
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<News> news = newsRepository.getItemsByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return getNewsDTOFromObject(news);
    }

    @Override
    @Transactional
    public NewsDTO findById(Long id) {
        News news = newsRepository.findById(id);
        if (news == null) {
            return null;
        }
        List<Comment> comments = news.getComments();
        comments.sort(new Comparator<Comment>() {
            public int compare(Comment o1, Comment o2) {
                return o2.getDate().toString().compareTo(o1.getDate().toString());
            }
        });
        news.setComments(comments);
        return getDTOFromObject(news);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        News news = newsRepository.findById(id);
        newsRepository.remove(news);
        return true;
    }

    @Override
    @Transactional
    public NewsDTO update(NewsDTO newsDTO) {
        News news = newsRepository.findById(newsDTO.getId());
        news.setDate(LocalDate.now());
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        newsRepository.merge(news);
        return getDTOFromObject(news);
    }

    private List<NewsDTO> getNewsDTOFromObject(List<News> newsList) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (News news : newsList) {
            newsDTOList.add(getDTOFromObject(news));
        }
        return newsDTOList;
    }

    private NewsDTO getDTOFromObject(News news) {
        return NewsConverterUtil.getDTOFromObject(news);
    }

    private News getObjectFromDTO(NewsDTO newsDTO) {
        return NewsConverterUtil.getObjectFromDTO(newsDTO);
    }

}
