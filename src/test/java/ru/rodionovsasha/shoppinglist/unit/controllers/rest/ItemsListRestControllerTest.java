package ru.rodionovsasha.shoppinglist.unit.controllers.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rodionovsasha.shoppinglist.controllers.rest.ItemsListRestController;
import ru.rodionovsasha.shoppinglist.controllers.rest.RestExceptionHandlerController;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.rodionovsasha.shoppinglist.Application.API_BASE_URL;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.*;
import static ru.rodionovsasha.shoppinglist.TestUtils.asJsonString;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemsListRestControllerTest {
    @Mock
    private ItemsListService itemsListService;
    private ItemsList itemsList;
    private List<ItemsList> itemsLists;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ItemsListRestController(itemsListService))
                .setControllerAdvice(new RestExceptionHandlerController())
                .build();
        Mockito.reset(itemsListService);
        itemsList = new ItemsList();
        itemsList.setId(LIST_ID);
        itemsList.setName(LIST_NAME);
        itemsLists = new ArrayList<>();
        itemsLists.add(itemsList);
    }

    @Test
    public void shouldGetAllListsTest() throws Exception {
        when(itemsListService.findAllLists()).thenReturn(itemsLists);
        mockMvc.perform(get(API_BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].name", is(LIST_NAME)));
        verify(itemsListService, times(1)).findAllLists();
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void getItemsListTest() throws Exception {
        when(itemsListService.getItemsListById(LIST_ID)).thenReturn(itemsList);
        mockMvc.perform(get(API_BASE_URL + ITEMS_LIST_BASE_PATH + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(LIST_NAME)))
                .andExpect(jsonPath("$.items.*", hasSize(0)));
        verify(itemsListService, times(1)).getItemsListById(LIST_ID);
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldSaveItemsListTest() throws Exception {
        mockMvc.perform(post(API_BASE_URL + ITEMS_LIST_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(itemsList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(LIST_NAME)));
        verify(itemsListService, times(1)).addItemsList(any(ItemsListDto.class));
        verifyNoMoreInteractions(itemsListService);}

    @Test
    public void shouldNotSaveItemsListIfHasErrorsTest() throws Exception {
        itemsList.setName("");
        mockMvc.perform(post(API_BASE_URL + ITEMS_LIST_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(itemsList)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.[0].field", is("name")))
                .andExpect(jsonPath("$.fieldErrors.[0].message", is("may not be empty")));
        verify(itemsListService, never()).addItemsList(any(ItemsListDto.class));
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldSaveEditedItemsListTest() throws Exception {
        itemsList.setName("new name");
        when(itemsListService.getItemsListById(LIST_ID)).thenReturn(itemsList);

        mockMvc.perform(put(API_BASE_URL + ITEMS_LIST_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(itemsList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("new name")));
        verify(itemsListService, times(1)).getItemsListById(LIST_ID);
        verify(itemsListService, times(1)).updateItemsList(any(ItemsListDto.class));
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldNotUpdateNotExistingListTest() throws Exception {
        itemsList.setId(100);
        mockMvc.perform(put(API_BASE_URL + ITEMS_LIST_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(itemsList)))
                .andExpect(status().isNotFound());
        verify(itemsListService, times(1)).getItemsListById(100);
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldNotSaveEditedItemsListIfHasErrorsTest() throws Exception {
        itemsList.setName("");
        mockMvc.perform(put(API_BASE_URL + ITEMS_LIST_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(itemsList)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.[0].field", is("name")))
                .andExpect(jsonPath("$.fieldErrors.[0].message", is("may not be empty")));
        verify(itemsListService, never()).updateItemsList(any(ItemsListDto.class));
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldDeleteItemsListTest() throws Exception {
        when(itemsListService.getItemsListById(LIST_ID)).thenReturn(itemsList);
        mockMvc.perform(delete(API_BASE_URL + ITEMS_LIST_BASE_PATH + "/{id}", LIST_ID))
                .andExpect(status().isOk());
        verify(itemsListService, times(1)).getItemsListById(LIST_ID);
        verify(itemsListService, times(1)).deleteItemsList(LIST_ID);
        verifyNoMoreInteractions(itemsListService);
    }

    @Test
    public void shouldNotDeleteNotExistingListTest() throws Exception {
        itemsList.setId(100);
        mockMvc.perform(delete(API_BASE_URL + ITEMS_LIST_BASE_PATH + "/{id}", 100)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(itemsList)))
                .andExpect(status().isNotFound());
        verify(itemsListService, times(1)).getItemsListById(100);
        verifyNoMoreInteractions(itemsListService);
    }
}