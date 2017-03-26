package ru.rodionovsasha.shoppinglist.unit.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.repositories.ItemRepository;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import ru.rodionovsasha.shoppinglist.services.ItemService;
import ru.rodionovsasha.shoppinglist.services.impl.ItemServiceImpl;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.*;

public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemsListRepository itemsListRepository;
    @Mock
    private ItemsList itemsList;
    private ItemDto itemDto;
    private ItemService itemService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        itemDto = new ItemDto();
        itemDto.setId(ITEM_ID);
        itemDto.setName(ITEM_NAME);
        itemDto.setListId(LIST_ID);
        itemDto.setComment("Comment");

        itemService = new ItemServiceImpl(itemRepository, itemsListRepository);
        Mockito.reset(itemRepository);
        Mockito.reset(itemsListRepository);
        Mockito.reset(itemsList);
    }

    @Test
    public void shouldAddItemTest() throws Exception {
        itemService.addItem(itemDto);
        verify(itemRepository, times(1)).saveAndFlush(any(Item.class));
        verify(itemsListRepository, times(1)).findOne(LIST_ID);
        verifyNoMoreInteractions(itemRepository);
        verifyNoMoreInteractions(itemsListRepository);
    }

    @Test
    public void shouldUpdateItemTest() throws Exception {
        when(itemRepository.findOne(ITEM_ID)).thenReturn(itemDto.toItem());
        Item item = itemService.getItemById(ITEM_ID);
        assertEquals(ITEM_NAME, item.getName());
        itemDto.setName("Updated name");

        itemService.updateItem(itemDto);

        verify(itemRepository, times(1)).saveAndFlush(any(Item.class));
        verify(itemRepository, times(2)).findOne(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
        assertEquals("Updated name", item.getName());
    }

    @Test
    public void shouldDeleteItemTest() throws Exception {
        itemService.deleteItem(ITEM_ID);
        verify(itemRepository, times(1)).delete(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void shouldGetItemByIdTest() throws Exception {
        itemService.getItemById(ITEM_ID);
        verify(itemRepository, times(1)).findOne(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void shouldToggleBoughtStatusTest() throws Exception {
        when(itemRepository.findOne(ITEM_ID)).thenReturn(itemDto.toItem());
        Item item = itemService.getItemById(ITEM_ID);
        assertFalse(item.isBought());

        itemService.toggleBoughtStatus(ITEM_ID);

        verify(itemRepository, times(2)).findOne(ITEM_ID);
        verify(itemRepository, times(1)).saveAndFlush(any(Item.class));
        verifyNoMoreInteractions(itemRepository);
        assertTrue(item.isBought());
    }
}