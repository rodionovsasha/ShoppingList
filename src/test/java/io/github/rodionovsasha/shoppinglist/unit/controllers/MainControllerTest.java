package io.github.rodionovsasha.shoppinglist.unit.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import io.github.rodionovsasha.shoppinglist.controllers.MainController;
import io.github.rodionovsasha.shoppinglist.entities.ItemsList;
import io.github.rodionovsasha.shoppinglist.services.ItemsListService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static io.github.rodionovsasha.shoppinglist.TestApplicationConfiguration.getViewResolver;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@ExtendWith(MockitoExtension.class)
class MainControllerTest {
    @Mock
    private ItemsListService itemsListService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MainController(itemsListService))
                .setViewResolvers(getViewResolver())
                .build();
    }

    @Test
    public void shouldGetAllListsTest() throws Exception {
        List<ItemsList> itemsLists = Collections.emptyList();
        when(itemsListService.findAllLists()).thenReturn(itemsLists);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("itemsLists", itemsLists));
        verify(itemsListService, times(1)).findAllLists();
        verifyNoMoreInteractions(itemsListService);
    }
}
