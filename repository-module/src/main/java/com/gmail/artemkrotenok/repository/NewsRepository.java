package com.gmail.artemkrotenok.repository;

import com.gmail.artemkrotenok.repository.model.News;

import java.util.List;

public interface NewsRepository extends GenericRepository<Long, News> {

    List<News> getItemsByPageSorted(int startPosition, int itemsByPage);

}
