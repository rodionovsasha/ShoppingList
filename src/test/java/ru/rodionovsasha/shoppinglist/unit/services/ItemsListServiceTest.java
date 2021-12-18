package ru.rodionovsasha.shoppinglist.unit.services;

import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.exceptions.NotFoundException;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;
import ru.rodionovsasha.shoppinglist.services.impl.ItemsListServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_ID;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_NAME;

public class ItemsListServiceTest {
    @Mock
    private ItemsListRepository itemsListRepository;
    private final ItemsList itemsList = new ItemsList();
    private ItemsListDto itemsListDto;
    private ItemsListService itemsListService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        itemsListDto = new ItemsListDto(LIST_NAME);
        itemsListDto.setId(LIST_ID);

        itemsListService = new ItemsListServiceImpl(itemsListRepository);
        Mockito.reset(itemsListRepository);
    }

    @Test
    public void shouldAddItemsListTest() {
        when(itemsListRepository.save(any(ItemsList.class))).thenReturn(itemsListDto.toItemsList());
        itemsListService.addItemsList(itemsListDto);
        verify(itemsListRepository, times(1)).save(any(ItemsList.class));
        verifyNoMoreInteractions(itemsListRepository);
    }

    @Test
    public void shouldUpdateItemsListTest() {
        when(itemsListRepository.findById(LIST_ID)).thenReturn(ofNullable(itemsListDto.toItemsList()));
        val itemsList = itemsListService.getItemsListById(LIST_ID);
        assertEquals(LIST_NAME, itemsList.getName());
        itemsListDto.setName("Updated name");

        itemsListService.updateItemsList(itemsListDto);

        verify(itemsListRepository, times(1)).save(any(ItemsList.class));
        verify(itemsListRepository, times(2)).findById(LIST_ID);
        verifyNoMoreInteractions(itemsListRepository);
        assertEquals("Updated name", itemsList.getName());
    }

    @Test
    public void shouldUpdateItemsListWhenNotFoundTest() {
        assertThrows(NotFoundException.class, () -> itemsListService.getItemsListById(LIST_ID));
    }

    @Test
    public void shouldDeleteItemsListTest() {
        itemsListService.deleteItemsList(LIST_ID);
        verify(itemsListRepository, times(1)).deleteById(LIST_ID);
        verifyNoMoreInteractions(itemsListRepository);
    }

    @Test
    public void shouldGetItemsListByIdTest() {
        when(itemsListRepository.findById(LIST_ID)).thenReturn(Optional.of(itemsList));
        itemsListService.getItemsListById(LIST_ID);
        verify(itemsListRepository, times(1)).findById(LIST_ID);
        verifyNoMoreInteractions(itemsListRepository);
    }

    @Test
    public void shouldNotGetItemsListByIdWhenNotFoundTest() {
        assertThrows(NotFoundException.class, () -> itemsListService.getItemsListById(LIST_ID));
    }

    @Test
    public void shouldFindAllListsTest() {
        val itemsLists = Collections.singletonList(itemsList);
        when(itemsListRepository.findAll()).thenReturn(itemsLists);
        val result = itemsListRepository.findAll();
        verify(itemsListRepository, times(1)).findAll();
        verifyNoMoreInteractions(itemsListRepository);
        assertEquals(1, ((Collection<ItemsList>) result).size());
    }
}