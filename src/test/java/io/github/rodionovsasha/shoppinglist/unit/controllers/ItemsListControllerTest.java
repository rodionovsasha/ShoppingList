package io.github.rodionovsasha.shoppinglist.unit.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import io.github.rodionovsasha.shoppinglist.controllers.ItemsListController;
import io.github.rodionovsasha.shoppinglist.dto.ItemsListDto;
import io.github.rodionovsasha.shoppinglist.entities.ItemsList;
import io.github.rodionovsasha.shoppinglist.services.ItemsListService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static io.github.rodionovsasha.shoppinglist.TestApplicationConfiguration.*;
import static io.github.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@ExtendWith(MockitoExtension.class)
class ItemsListControllerTest {
    @Mock
    private ItemsListService itemsListService;
    @Mock
    private ItemsList itemsList;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ItemsListController(itemsListService))
                .setViewResolvers(getViewResolver())
                .build();
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
        verifyNoMoreInteractions(itemsListService);
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
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldNotSaveItemsListIfHasErrorsTest() throws Exception {
        mockMvc.perform(post(ITEMS_LIST_BASE_PATH + "/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("addItemsList"));
        verify(itemsListService, never()).addItemsList(any(ItemsListDto.class));
        verifyNoMoreInteractions(itemsListService);
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
        verifyNoMoreInteractions(itemsListService);
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
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldNotSaveEditedItemsListIfHasErrorsTest() throws Exception {
        mockMvc.perform(post(ITEMS_LIST_BASE_PATH + "/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", LIST_ID_PARAM))
                .andExpect(status().isOk())
                .andExpect(view().name("editItemsList"));
        verify(itemsListService, never()).updateItemsList(any(ItemsListDto.class));
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldDeleteItemsListTest() throws Exception {
        mockMvc.perform(get(ITEMS_LIST_BASE_PATH + "/delete")
                .param("id", LIST_ID_PARAM))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(itemsListService, times(1)).deleteItemsList(LIST_ID);
        verifyNoMoreInteractions(itemsListService);
    }
}
