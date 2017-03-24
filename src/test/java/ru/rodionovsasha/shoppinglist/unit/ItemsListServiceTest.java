package ru.rodionovsasha.shoppinglist.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;
import ru.rodionovsasha.shoppinglist.services.impl.ItemsListServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.*;

public class ItemsListServiceTest {
    @Mock
    private ItemsListRepository itemsListRepository;
    private ItemsList itemsList = new ItemsList();
    private ItemsListDto itemsListDto;
    private ItemsListService itemsListService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        itemsListDto = new ItemsListDto();
        itemsListDto.setId(ITEM_ID);
        itemsListDto.setName(LIST_NAME);

        itemsListService = new ItemsListServiceImpl(itemsListRepository);
        Mockito.reset(itemsListRepository);
    }

    @Test
    public void shouldAddItemsListTest() throws Exception {
        when(itemsListRepository.saveAndFlush(any(ItemsList.class))).thenReturn(itemsListDto.toItemsList());
        itemsListService.addItemsList(itemsListDto);
        verify(itemsListRepository, times(1)).saveAndFlush(any(ItemsList.class));
    }

    @Test
    public void shouldUpdateItemsListTest() throws Exception {
        when(itemsListRepository.findOne(LIST_ID)).thenReturn(itemsListDto.toItemsList());
        ItemsList itemsList = itemsListService.getItemsListById(LIST_ID);
        assertEquals(LIST_NAME, itemsList.getName());
        itemsListDto.setName("Updated name");

        itemsListService.updateItemsList(itemsListDto);

        verify(itemsListRepository, times(1)).saveAndFlush(any(ItemsList.class));
        verify(itemsListRepository, times(2)).findOne(LIST_ID);
        assertEquals("Updated name", itemsList.getName());
    }

    @Test
    public void shouldDeleteItemsListTest() throws Exception {
        itemsListService.deleteItemsList(LIST_ID);
        verify(itemsListRepository, times(1)).delete(LIST_ID);
    }

    @Test
    public void shouldGetItemsListByIdTest() throws Exception {
        itemsListService.getItemsListById(LIST_ID);
        verify(itemsListRepository, times(1)).findOne(LIST_ID);
    }

    @Test
    public void shouldFindAllListsTest() throws Exception {
        List<ItemsList> itemsLists = Collections.singletonList(itemsList);
        when(itemsListRepository.findAll()).thenReturn(itemsLists);
        List<ItemsList> result = itemsListRepository.findAll();
        verify(itemsListRepository, times(1)).findAll();
        assertEquals(1, result.size());
    }
}