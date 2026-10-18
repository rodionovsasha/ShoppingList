package io.github.rodionovsasha.shoppinglist.unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.rodionovsasha.shoppinglist.dto.ItemDto;
import io.github.rodionovsasha.shoppinglist.entities.Item;
import io.github.rodionovsasha.shoppinglist.entities.ItemsList;
import io.github.rodionovsasha.shoppinglist.exceptions.NotFoundException;
import io.github.rodionovsasha.shoppinglist.repositories.ItemRepository;
import io.github.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import io.github.rodionovsasha.shoppinglist.services.ItemService;
import io.github.rodionovsasha.shoppinglist.services.impl.ItemServiceImpl;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static io.github.rodionovsasha.shoppinglist.TestApplicationConfiguration.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemsListRepository itemsListRepository;
    @Mock
    private ItemsList itemsList;
    private ItemDto itemDto;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemDto = new ItemDto();
        itemDto.setId(ITEM_ID);
        itemDto.setName(ITEM_NAME);
        itemDto.setListId(LIST_ID);
        itemDto.setComment("Comment");

        itemService = new ItemServiceImpl(itemRepository, itemsListRepository);
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

    @Test
    public void shouldNotAddItemWhenListNotExistTest() {
        assertThrows(NotFoundException.class, () -> itemService.addItem(itemDto));
    }

    @Test
    public void shouldUpdateItemTest() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(ofNullable(itemDto.toItem()));
        var item = itemService.getItemById(ITEM_ID);
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

    @Test
    public void shouldNotGetItemByIdWhenNotFoundTest() {
        assertThrows(NotFoundException.class, () -> itemService.getItemById(ITEM_ID));
    }

    @Test
    public void shouldNotUpdateItemWhenNotFoundTest() {
        assertThrows(NotFoundException.class, () -> itemService.updateItem(itemDto));
        verify(itemRepository).findById(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void shouldToggleBoughtStatusTest() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(ofNullable(itemDto.toItem()));
        var item = itemService.getItemById(ITEM_ID);
        assertFalse(item.isBought());

        itemService.toggleBoughtStatus(ITEM_ID);

        verify(itemRepository, times(2)).findById(ITEM_ID);
        verify(itemRepository, times(1)).save(any(Item.class));
        verifyNoMoreInteractions(itemRepository);
        assertTrue(item.isBought());

        itemService.toggleBoughtStatus(ITEM_ID);
        assertFalse(item.isBought());
    }

    @Test
    public void shouldNotToggleBoughtStatusWhenItemNotFoundTest() {
        assertThrows(NotFoundException.class, () -> itemService.toggleBoughtStatus(ITEM_ID));
        verify(itemRepository).findById(ITEM_ID);
        verifyNoMoreInteractions(itemRepository);
    }
}
