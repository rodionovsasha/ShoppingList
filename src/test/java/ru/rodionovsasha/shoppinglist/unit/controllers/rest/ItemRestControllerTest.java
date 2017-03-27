package ru.rodionovsasha.shoppinglist.unit.controllers.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rodionovsasha.shoppinglist.controllers.rest.ItemRestController;
import ru.rodionovsasha.shoppinglist.controllers.rest.RestExceptionHandlerController;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemService;

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
import static ru.rodionovsasha.shoppinglist.controllers.ItemController.ITEM_BASE_PATH;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

public class ItemRestControllerTest {
    @Mock
    private ItemService itemService;
    private Item item;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ItemRestController(itemService))
                .setControllerAdvice(new RestExceptionHandlerController())
                .build();
        Mockito.reset(itemService);
        ItemsList itemsList = new ItemsList();
        itemsList.setId(LIST_ID);
        item = new Item();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setComment("Comment");
        item.setBought(true);
        item.setItemsList(itemsList);
    }

    @Test
    public void shouldGetItemTest() throws Exception {
        when(itemService.getItemById(ITEM_ID)).thenReturn(item);
        mockMvc.perform(get(API_BASE_URL + ITEM_BASE_PATH + "/{id}", ITEM_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(ITEM_NAME)))
                .andExpect(jsonPath("$.comment", is("Comment")))
                .andExpect(jsonPath("$.itemsList.id", is(1)))
                .andExpect(jsonPath("$.bought", is(true)));
        verify(itemService, times(1)).getItemById(ITEM_ID);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldAddItemTest() throws Exception {
        mockMvc.perform(post(API_BASE_URL + ITEM_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(item)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.comment", is("Comment")))
                .andExpect(jsonPath("$.name", is(ITEM_NAME)));
        verify(itemService, times(1)).addItem(any(ItemDto.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldNotSaveItemIfHasErrorsTest() throws Exception {
        item.setName("");
        mockMvc.perform(post(API_BASE_URL + ITEM_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(item)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.[0].field", is("name")))
                .andExpect(jsonPath("$.fieldErrors.[0].message", is("may not be empty")));
        verify(itemService, never()).addItem(any(ItemDto.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldSaveEditedItemTest() throws Exception {
        item.setName("new name");
        when(itemService.getItemById(ITEM_ID)).thenReturn(item);

        mockMvc.perform(put(API_BASE_URL + ITEM_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(item)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("new name")));
        verify(itemService, times(1)).getItemById(ITEM_ID);
        verify(itemService, times(1)).updateItem(any(ItemDto.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldNotUpdateNotExistingListTest() throws Exception {
        item.setId(100);
        mockMvc.perform(put(API_BASE_URL + ITEM_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(item)))
                .andExpect(status().isNotFound());
        verify(itemService, times(1)).getItemById(100);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldNotSaveEditedItemIfHasErrorsTest() throws Exception {
        item.setName("");
        mockMvc.perform(put(API_BASE_URL + ITEM_BASE_PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(item)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.[0].field", is("name")))
                .andExpect(jsonPath("$.fieldErrors.[0].message", is("may not be empty")));
        verify(itemService, never()).updateItem(any(ItemDto.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldDeleteItemTest() throws Exception {
        when(itemService.getItemById(ITEM_ID)).thenReturn(item);
        mockMvc.perform(delete(API_BASE_URL + ITEM_BASE_PATH + "/{id}", ITEM_ID))
                .andExpect(status().isOk());
        verify(itemService, times(1)).getItemById(ITEM_ID);
        verify(itemService, times(1)).deleteItem(ITEM_ID);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldNotDeleteNotExistingItemTest() throws Exception {
        item.setId(100);
        mockMvc.perform(delete(API_BASE_URL + ITEM_BASE_PATH + "/{id}", 100)
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(item)))
                .andExpect(status().isNotFound());
        verify(itemService, times(1)).getItemById(100);
        verifyNoMoreInteractions(itemService);
    }
}