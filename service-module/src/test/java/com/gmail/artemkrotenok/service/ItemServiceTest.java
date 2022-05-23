package com.gmail.artemkrotenok.service;

import java.math.BigDecimal;

import com.gmail.artemkrotenok.repository.ItemRepository;
import com.gmail.artemkrotenok.repository.model.Item;
import com.gmail.artemkrotenok.service.impl.ItemServiceImpl;
import com.gmail.artemkrotenok.service.model.ItemDTO;
import com.gmail.artemkrotenok.service.util.ItemConverterUtil;
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
class ItemServiceTest {

    public static final long TEST_ITEM_ID = 2L;
    public static final Boolean TEST_ITEM_DELETED_STATUS = false;
    public static final String TEST_ITEM_NAME = "TestName";
    public static final String TEST_ITEM_DESCRIPTION = "Test description";
    public static final String TEST_ITEM_UN = "4dddc455-87f1-4a70-ae2d-b42f6cd3ae73";
    public static final BigDecimal TEST_ITEM_PRICE = BigDecimal.valueOf(123456789);

    @Mock
    private ItemRepository itemRepository;

    private ItemService itemService;

    @BeforeEach
    public void setup() {
        this.itemService = new ItemServiceImpl(itemRepository);
    }

    @Test
    public void add_returnItemDTO() {
        ItemDTO itemDTO = getValidItemDTO();
        doNothing().when(itemRepository).persist(any(Item.class));
        ItemDTO addedItemDTO = itemService.add(itemDTO);
        Assertions.assertThat(addedItemDTO).isNotNull();
        Assertions.assertThat(addedItemDTO.getDescription()).isEqualTo(itemDTO.getDescription());
        verify(itemRepository, times(1)).persist(any(Item.class));
    }

    @Test
    public void getItemById_returnItemDTO() {
        Item item = getEmptyItem();
        when(itemRepository.findById(TEST_ITEM_ID)).thenReturn(item);
        ItemDTO itemDTO = itemService.findById(TEST_ITEM_ID);
        verify(itemRepository, times(1)).findById(TEST_ITEM_ID);
        Assertions.assertThat(itemDTO).isNotNull();
    }

    @Test
    public void copyById_returnCopyItemDTO() {
        ItemDTO itemDTO = getValidItemDTO();
        Item item = getValidItem();
        when(itemRepository.findById(TEST_ITEM_ID)).thenReturn(item);
        ItemDTO copyItemDTO = itemService.copyById(TEST_ITEM_ID);
        verify(itemRepository, times(1)).findById(TEST_ITEM_ID);
        Assertions.assertThat(copyItemDTO.getDescription()).isEqualTo(itemDTO.getDescription());
        Assertions.assertThat(copyItemDTO.getName()).isNotEqualTo((itemDTO.getName()));
    }

    @Test
    public void getItemById_returnNull() {
        when(itemRepository.findById(TEST_ITEM_ID)).thenReturn(null);
        ItemDTO itemDTO = itemService.findById(TEST_ITEM_ID);
        verify(itemRepository, times(1)).findById(TEST_ITEM_ID);
        Assertions.assertThat(itemDTO).isNull();
    }

    private Item getEmptyItem() {
        return new Item();
    }

    private Item getValidItem() {
        Item item = new Item();
        item.setId(TEST_ITEM_ID);
        item.setName(TEST_ITEM_NAME);
        item.setDescription(TEST_ITEM_DESCRIPTION);
        item.setPrice(TEST_ITEM_PRICE);
        item.setUniqueNumber(TEST_ITEM_UN);
        item.setDeleted(TEST_ITEM_DELETED_STATUS);
        return item;
    }

    private ItemDTO getValidItemDTO() {
        return ItemConverterUtil.getDTOFromObject(getValidItem());
    }

}
