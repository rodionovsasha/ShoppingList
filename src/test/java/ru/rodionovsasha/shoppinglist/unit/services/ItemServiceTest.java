package ru.rodionovsasha.shoppinglist.unit.services;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.exceptions.NotFoundException;
import ru.rodionovsasha.shoppinglist.repositories.ItemRepository;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import ru.rodionovsasha.shoppinglist.services.ItemService;
import ru.rodionovsasha.shoppinglist.services.impl.ItemServiceImpl;

import static java.util.Optional.ofNullable;
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
    public void setUp() {
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
    public void shouldAddItemTest() {
        when(itemsListRepository.findById(LIST_ID)).thenReturn(ofNullable(itemsList));
        itemService.addItem(itemDto);
        verify(itemRepository, times(1)).save(any(Item.class));
        verify(itemsListRepository, times(1)).findById(LIST_ID);
        verifyNoMoreInteractions(itemRepository);
        verifyNoMoreInteractions(itemsListRepository);
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotAddItemWhenListNotExistTest() {
        itemService.addItem(itemDto);
    }

    @Test
    public void shouldUpdateItemTest() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(ofNullable(itemDto.toItem()));
        val item = itemService.getItemById(ITEM_ID);
        assertEquals(ITEM_NAME, item.getName());
        itemDto.setName("Updated name");

        itemService.updateItem(itemDto);

        verify(itemRepository, times(1)).save(any(Item.class));
        verify(itemRepository, times(2)).findById(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
        assertEquals("Updated name", item.getName());
    }

    @Test
    public void shouldDeleteItemTest() {
        itemService.deleteItem(ITEM_ID);
        verify(itemRepository, times(1)).deleteById(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void shouldGetItemByIdTest() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(ofNullable(itemDto.toItem()));
        itemService.getItemById(ITEM_ID);
        verify(itemRepository, times(1)).findById(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotGetItemByIdWhenNotFoundTest() {
        itemService.getItemById(ITEM_ID);
    }

    @Test
    public void shouldToggleBoughtStatusTest() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(ofNullable(itemDto.toItem()));
        val item = itemService.getItemById(ITEM_ID);
        assertFalse(item.isBought());

        itemService.toggleBoughtStatus(ITEM_ID);

        verify(itemRepository, times(2)).findById(ITEM_ID);
        verify(itemRepository, times(1)).save(any(Item.class));
        verifyNoMoreInteractions(itemRepository);
        assertTrue(item.isBought());

        itemService.toggleBoughtStatus(ITEM_ID);
        assertFalse(item.isBought());
    }
}