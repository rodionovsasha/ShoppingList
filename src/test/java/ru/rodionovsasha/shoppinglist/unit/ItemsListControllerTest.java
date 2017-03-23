package ru.rodionovsasha.shoppinglist.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rodionovsasha.shoppinglist.controllers.ItemsListController;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.*;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemsListControllerTest {
    @Mock
    private ItemsListService itemsListService;
    @Mock
    private ItemsList itemsList;
    private List<ItemsList> itemsLists;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ItemsListController(itemsListService))
                .setViewResolvers(getViewResolver())
                .build();
        Mockito.reset(itemsListService);
        Mockito.reset(itemsList);
        itemsLists = new ArrayList<>();
        itemsLists.add(itemsList);
    }

    @Test
    public void shouldGetAllListsTest() throws Exception {
        when(itemsListService.findAllLists()).thenReturn(itemsLists);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("itemsLists", itemsLists));
        verify(itemsListService, times(1)).findAllLists();
    }

    @Test
    public void getItemsListTest() throws Exception {
        when(itemsListService.getItemsListById(LIST_ID)).thenReturn(itemsList);
        mockMvc.perform(get(ITEMS_LIST_BASE_PATH)
                .param("id", LIST_ID_PARAM))
                .andExpect(status().isOk())
                .andExpect(view().name("itemsList"))
                .andExpect(model().attribute("itemsList", itemsList));
        verify(itemsListService, times(1)).getItemsListById(LIST_ID);
    }

    @Test
    public void shouldAddItemsListFormTest() throws Exception {
        mockMvc.perform(get(ITEMS_LIST_BASE_PATH + "/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addItemsList"));
    }

    @Test
    public void shouldSaveItemsListTest() throws Exception {
        when(itemsListService.addItemsList(any(ItemsListDto.class))).thenReturn(LIST_ID);
        mockMvc.perform(post(ITEMS_LIST_BASE_PATH + "/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", LIST_NAME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ITEMS_LIST_BASE_PATH + "?id=" + LIST_ID));
        verify(itemsListService, times(1)).addItemsList(any(ItemsListDto.class));
    }

    @Test
    public void shouldNotSaveItemsListIfHasErrorsTest() throws Exception {
        mockMvc.perform(post(ITEMS_LIST_BASE_PATH + "/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("addItemsList"));
        verify(itemsListService, never()).addItemsList(any(ItemsListDto.class));
    }

    @Test
    public void shouldShowEditItemsListFormTest() throws Exception {
        when(itemsListService.getItemsListById(LIST_ID)).thenReturn(itemsList);
        mockMvc.perform(get(ITEMS_LIST_BASE_PATH + "/edit")
                .param("id", LIST_ID_PARAM))
                .andExpect(status().isOk())
                .andExpect(view().name("editItemsList"))
                .andExpect(model().attribute("itemsListDto", itemsList));
        verify(itemsListService, times(1)).getItemsListById(LIST_ID);
    }

    @Test
    public void shouldSaveEditedItemsListTest() throws Exception {
        mockMvc.perform(post(ITEMS_LIST_BASE_PATH + "/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", LIST_ID_PARAM)
                .param("name", ITEM_NAME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ITEMS_LIST_BASE_PATH + "?id=" + LIST_ID));
        verify(itemsListService, times(1)).updateItemsList(any(ItemsListDto.class));
    }

    @Test
    public void shouldNotSaveEditedItemsListIfHasErrorsTest() throws Exception {
        mockMvc.perform(post(ITEMS_LIST_BASE_PATH + "/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", LIST_ID_PARAM))
                .andExpect(status().isOk())
                .andExpect(view().name("editItemsList"));
        verify(itemsListService, never()).updateItemsList(any(ItemsListDto.class));
    }

    @Test
    public void shouldDeleteItemsListTest() throws Exception {
        mockMvc.perform(get(ITEMS_LIST_BASE_PATH + "/delete")
                .param("id", LIST_ID_PARAM))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(itemsListService, times(1)).deleteItemsList(LIST_ID);
    }
}