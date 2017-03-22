package ru.rodionovsasha.shoppinglist.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rodionovsasha.shoppinglist.controllers.ItemController;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemService;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.ITEM_NAME;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_ID;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.getViewResolver;
import static ru.rodionovsasha.shoppinglist.controllers.ItemController.ITEM_BASE_PATH;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemControllerTest {
    private static final long ITEM_ID = 1;
    private static final String ITEM_ID_PARAM = Long.toString(ITEM_ID);

    @Mock
    private ItemService itemService;
    @Mock
    private Item item;
    @Mock
    private ItemsList itemsList;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ItemController(itemService))
                .setViewResolvers(getViewResolver())
                .build();
        Mockito.reset(itemService);
    }

    @Test
    public void getItemTest() throws Exception {
        when(itemService.getItemById(ITEM_ID)).thenReturn(item);

        mockMvc.perform(get(ITEM_BASE_PATH).param("id", ITEM_ID_PARAM))
                .andExpect(status().isOk())
                .andExpect(view().name("item"))
                .andExpect(model().attribute("item", item));
        verify(itemService, times(1)).getItemById(ITEM_ID);
    }

    @Test
    public void addItemFormTest() throws Exception {
        mockMvc.perform(get(ITEM_BASE_PATH + "/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addItem"));
    }

    @Test
    public void shouldSaveItemTest() throws Exception {
        mockMvc.perform(post(ITEM_BASE_PATH + "/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ITEM_NAME)
                .param("listId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ITEMS_LIST_BASE_PATH + "?id=" + LIST_ID));
        verify(itemService, times(1)).addItem(any(ItemDto.class));
    }

    @Test
    public void shouldNotSaveItemWithoutNameTest() throws Exception {
        mockMvc.perform(post(ITEM_BASE_PATH + "/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("addItem"));
        verify(itemService, never()).addItem(any(ItemDto.class));
    }

    @Test
    public void shouldShowEditItemFormTest() throws Exception {
        when(itemService.getItemById(ITEM_ID)).thenReturn(item);
        when(item.getItemsList()).thenReturn(itemsList);
        when(itemsList.getId()).thenReturn(LIST_ID);

        mockMvc.perform(get(ITEM_BASE_PATH + "/edit")
                .param("id", ITEM_ID_PARAM))
                .andExpect(status().isOk())
                .andExpect(view().name("editItem"))
                .andExpect(model().attribute("itemDto", item))
                .andExpect(model().attribute("listId", LIST_ID));
        verify(itemService, times(1)).getItemById(ITEM_ID);
    }

    @Test
    public void shouldSaveEditedItemTest() throws Exception {
        mockMvc.perform(post(ITEM_BASE_PATH + "/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ITEM_ID_PARAM)
                .param("name", ITEM_NAME)
                .param("listId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ITEMS_LIST_BASE_PATH + "?id=" + LIST_ID));
        verify(itemService, times(1)).updateItem(any(ItemDto.class));
    }

    @Test
    public void shouldNotSaveEditedItemIfHasErrorsTest() throws Exception {
        mockMvc.perform(post(ITEM_BASE_PATH + "/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ITEM_ID_PARAM)
                .param("listId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editItem"))
                .andExpect(model().attribute("listId", LIST_ID));
        verify(itemService, never()).updateItem(any(ItemDto.class));
    }

    @Test
    public void shouldToggleItemBoughtStatusTest() throws Exception {
        mockMvc.perform(get(ITEM_BASE_PATH + "/bought")
                .param("id", ITEM_ID_PARAM)
                .param("listId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ITEMS_LIST_BASE_PATH + "?id=" + LIST_ID));
        verify(itemService, times(1)).toggleBoughtStatus(LIST_ID);
    }

    @Test
    public void shouldDeleteItemTest() throws Exception {
        mockMvc.perform(get(ITEM_BASE_PATH + "/delete")
                .param("id", ITEM_ID_PARAM)
                .param("listId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ITEMS_LIST_BASE_PATH + "?id=" + LIST_ID));
        verify(itemService, times(1)).deleteItem(ITEM_ID);
    }
}