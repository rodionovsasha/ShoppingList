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
    @Mock
    private ItemService itemService;
    @Mock
    private Item item;

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

        mockMvc.perform(get(ITEM_BASE_PATH).param("id", "1"))
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
        //WHEN
  /*      String result = controller.showEditItemForm(ITEM_ID, modelMap);
        //THEN
        assertEquals("editItem", result);
        assertTrue(modelMap.containsKey(EDIT_ITEM_FORM_NAME));*/
    }

    @Test
    public void shouldSaveEditedItemTest() throws Exception {
        //WHEN
/*        String result = controller.saveEditedItem(item, bindingResult, redirectAttributes, LIST_ID);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(itemService, times(1)).updateItem(ITEM_ID, item.getName(), item.getComment());*/
    }

    @Test
    public void shouldNotSaveEditedItemIfHasErrorsTest() throws Exception {
        //GIVEN
      //  when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
/*        String result = controller.saveEditedItem(item, bindingResult, redirectAttributes, LIST_ID);
        //THEN
        assertEquals("redirect:/item/edit?id=" + ITEM_ID, result);
        verify(itemService, times(0)).updateItem(ITEM_ID, item.getName(), item.getComment());
        verify(redirectAttributes, times(1)).addFlashAttribute("org.springframework.validation.BindingResult." + EDIT_ITEM_FORM_NAME, bindingResult);
        verify(redirectAttributes, times(1)).addFlashAttribute(EDIT_ITEM_FORM_NAME, item);
        verify(redirectAttributes, times(1)).addFlashAttribute("listId", LIST_ID);*/
    }

    @Test
    public void shouldToggleItemBoughtStatusTest() throws Exception {
        //GIVEN
        when(item.isBought()).thenReturn(true);
        //WHEN
/*        String result = controller.toggleItemBoughtStatus(ITEM_ID, LIST_ID);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(itemService, times(1)).toggleBoughtStatus(ITEM_ID);*/
    }

    @Test
    public void shouldDeleteItemTest() throws Exception {
        //WHEN
/*        String result = controller.deleteItem(ITEM_ID, LIST_ID);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(itemService, times(1)).deleteItem(ITEM_ID);*/
    }
}