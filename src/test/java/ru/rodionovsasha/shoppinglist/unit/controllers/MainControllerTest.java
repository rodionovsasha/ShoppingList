package ru.rodionovsasha.shoppinglist.unit.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rodionovsasha.shoppinglist.controllers.MainController;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.getViewResolver;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

public class MainControllerTest {
    @Mock
    private ItemsListService itemsListService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MainController(itemsListService))
                .setViewResolvers(getViewResolver())
                .build();
        Mockito.reset(itemsListService);
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
