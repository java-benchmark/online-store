package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.service.model.NewsDTO;

import java.util.List;

public interface NewsService {

    NewsDTO add(NewsDTO newsDTO);

    Long getCountNews();

    List<NewsDTO> getItemsByPageSorted(Integer page);

    NewsDTO findById(Long id);

    boolean deleteById(Long id);

    NewsDTO update(NewsDTO newsDTO);

}
