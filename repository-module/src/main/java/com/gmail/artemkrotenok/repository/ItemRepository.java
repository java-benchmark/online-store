package com.gmail.artemkrotenok.repository;

import java.util.List;

import com.gmail.artemkrotenok.repository.model.Item;

public interface ItemRepository extends GenericRepository<Long, Item> {

    List<Item> getItemsByPageSorted(int startPosition, int itemsByPage);

}
