package com.gmail.artemkrotenok.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.artemkrotenok.repository.ItemRepository;
import com.gmail.artemkrotenok.repository.model.Item;
import com.gmail.artemkrotenok.service.ItemService;
import com.gmail.artemkrotenok.service.model.ItemDTO;
import com.gmail.artemkrotenok.service.util.ItemConverterUtil;
import com.gmail.artemkrotenok.service.util.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public static final String COPY_NAME_POSTFIX = " COPY";

    private final ItemRepository itemRepository;

    public ItemServiceImpl(
            ItemRepository itemRepository
    ) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public ItemDTO add(ItemDTO itemDTO) {
        Item item = getObjectFromDTO(itemDTO);
        item.setDeleted(false);
        if (item.getUniqueNumber() == null) {
            item.setUniqueNumber(UUID.randomUUID().toString());
        }
        itemRepository.persist(item);
        return getDTOFromObject(item);
    }

    @Override
    @Transactional
    public Long getCountItem() {
        return itemRepository.getCount();
    }

    @Override
    @Transactional
    public List<ItemDTO> getItemsByPageSorted(Integer page) {
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<Item> items = itemRepository.getItemsByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return convertItemsToItemsDTO(items);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Item item = itemRepository.findById(id);
        itemRepository.remove(item);
        return true;
    }

    @Override
    @Transactional
    public ItemDTO findById(Long id) {
        Item item = itemRepository.findById(id);
        if (item == null) {
            return null;
        }
        return getDTOFromObject(item);
    }

    @Override
    @Transactional
    public ItemDTO copyById(Long itemId) {
        Item item = itemRepository.findById(itemId);
        Item copyItem = getCopy(item);
        itemRepository.merge(copyItem);
        return getDTOFromObject(copyItem);
    }

    @Override
    @Transactional
    public int addItemsAsJSON(String pathFile) {
        ObjectMapper mapper = new ObjectMapper();
        List<ItemDTO> itemsDTO;
        int countAddedItem = 0;
        File file = new File(pathFile);
        try {
            File f = new File(pathFile);
            itemsDTO = Arrays.asList(mapper.readValue(f, ItemDTO[].class));
        } catch (IOException e) {
            logger.error("Error read item from file: " + pathFile);
            return countAddedItem;
        }
        file.delete();
        for (ItemDTO itemDTO : itemsDTO) {
            if (add(itemDTO) != null) {
                countAddedItem++;
            }
        }
        return countAddedItem;
    }

    private Item getCopy(Item item) {
        Item copyItem = new Item();
        copyItem.setName(item.getName() + COPY_NAME_POSTFIX);
        copyItem.setDescription(item.getDescription());
        copyItem.setPrice(item.getPrice());
        copyItem.setUniqueNumber(UUID.randomUUID().toString());
        return copyItem;
    }

    private List<ItemDTO> convertItemsToItemsDTO(List<Item> items) {
        return items.stream()
                .map(this::getDTOFromObject)
                .collect(Collectors.toList());
    }

    private ItemDTO getDTOFromObject(Item item) {
        return ItemConverterUtil.getDTOFromObject(item);
    }

    private Item getObjectFromDTO(ItemDTO itemDTO) {
        return ItemConverterUtil.getObjectFromDTO(itemDTO);
    }

}
