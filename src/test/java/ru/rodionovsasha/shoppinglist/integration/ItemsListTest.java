package ru.rodionovsasha.shoppinglist.integration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_ID;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_NAME;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.EDIT_ITEMS_LIST_FORM_NAME;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_FORM_NAME;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemsListTest extends BaseIntegrationTest {
    @Autowired
    private ItemsListService itemsListService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ItemsList itemsList;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        itemsList = itemsListService.findOneItemsListById(LIST_ID);
    }

    @Test
    public void shouldFindAllListsTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/").accept(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("itemsLists"))
                .andExpect(model().attribute("itemsLists", hasItem(itemsList)));
    }

    @Test
    public void shouldFindOneItemsListTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/itemsList?id=" + LIST_ID).accept(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(view().name("itemsList"))
                .andExpect(model().attributeExists("itemsList"));
    }

    @Test
    public void shouldShowAddItemsListFormTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/itemsList/add").accept(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(view().name("addItemsList"))
                .andExpect(model().attributeExists(ITEMS_LIST_FORM_NAME));
    }

    @Test
    public void shouldSaveItemsListTest() throws Exception {
        mockMvc.perform(post("/itemsList/add")
                .param("name", "List name"))
                .andExpect(redirectedUrl("/itemsList?id=2"));
        assertNotNull(itemsListService.findOneItemsListByName("List name"));
    }

    @Test
    public void shouldNotSaveItemsListTest() throws Exception {
        mockMvc.perform(post("/itemsList/add")
                .param("name", ""))
                .andExpect(redirectedUrl("/itemsList/add"));
        assertNull(itemsListService.findOneItemsListByName("List name"));
    }

    @Test
    public void shouldShowEditItemsListFormTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/itemsList/edit?id=" + LIST_ID).accept(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(view().name("editItemsList"))
                .andExpect(model().attributeExists(EDIT_ITEMS_LIST_FORM_NAME));
    }

    @Test
    public void shouldEditItemsListTest() throws Exception {
        mockMvc.perform(post("/itemsList/edit")
                .param("id", String.valueOf(LIST_ID))
                .param("name", "Edited name"))
                .andExpect(redirectedUrl("/itemsList?id=" + LIST_ID));
        assertEquals("Edited name", itemsList.getName());
    }

    @Test
    public void shouldNotSaveEditedItemsListTest() throws Exception {
        mockMvc.perform(post("/itemsList/edit")
                .param("id", String.valueOf(LIST_ID))
                .param("name", ""))
                .andExpect(redirectedUrl("/itemsList/edit?id=" + LIST_ID));
        assertEquals(LIST_NAME, itemsList.getName());
    }

    @Test
    public void shouldDeleteItemsListTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/itemsList/delete?id=" + LIST_ID).accept(contentType))
                .andExpect(status().is3xxRedirection());
        assertNull(itemsListService.findOneItemsListById(LIST_ID));
    }
}