package ru.rodionovsasha.shoppinglist.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rodionovsasha.shoppinglist.controllers.ItemController;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemService;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.ITEM_ID;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_ID;
import static ru.rodionovsasha.shoppinglist.controllers.ItemController.EDIT_ITEM_FORM_NAME;
import static ru.rodionovsasha.shoppinglist.controllers.ItemController.ITEM_FORM_NAME;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemControllerTest extends BaseUnitTest {
    @Mock
    private ItemService itemService;
    @Mock
    private Item item;
    @Mock
    private ItemsList itemsList;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;

    private ModelMap modelMap;
    private ItemController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ItemController(itemService);
        modelMap = new ModelMap();
        when(itemService.findOneItemById(anyLong())).thenReturn(item);
        when(item.getId()).thenReturn(ITEM_ID);
        when(item.getItemsList()).thenReturn(itemsList);
        when(itemsList.getId()).thenReturn(LIST_ID);
    }

    @Test
    public void getItemTest() throws Exception {
        //WHEN
        String result = controller.getItem(ITEM_ID, modelMap);
        //THEN
        assertEquals("item", result);
        assertTrue(modelMap.containsKey("item"));
        verify(itemService, times(1)).findOneItemById(ITEM_ID);
    }

    @Test
    public void addItemTest() throws Exception {
        //WHEN
        String result = controller.addItem(LIST_ID, modelMap);
        //THEN
        assertEquals("addItem", result);
        assertTrue(modelMap.containsKey(ITEM_FORM_NAME));
        assertTrue(modelMap.containsKey("listId"));
    }

    @Test
    public void shouldSaveItemTest() throws Exception {
        //WHEN
        String result = controller.saveItem(item, bindingResult, redirectAttributes, LIST_ID);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(itemService, times(1)).saveNewItem(item, LIST_ID);
    }

    @Test
    public void shouldNotSaveItemIfHasErrorsTest() throws Exception {
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String result = controller.saveItem(item, bindingResult, redirectAttributes, LIST_ID);
        //THEN
        assertEquals("redirect:/item/add", result);
        verify(itemService, times(0)).saveNewItem(item, LIST_ID);
        verify(redirectAttributes, times(1)).addFlashAttribute("org.springframework.validation.BindingResult." + ITEM_FORM_NAME, bindingResult);
        verify(redirectAttributes, times(1)).addFlashAttribute(ITEM_FORM_NAME, item);
        verify(redirectAttributes, times(1)).addFlashAttribute("listId", LIST_ID);
        verify(redirectAttributes, times(1)).addAttribute("listId", LIST_ID);
    }

    @Test
    public void shouldShowEditItemFormTest() throws Exception {
        //WHEN
        String result = controller.showEditItemForm(ITEM_ID, modelMap);
        //THEN
        assertEquals("editItem", result);
        assertTrue(modelMap.containsKey(EDIT_ITEM_FORM_NAME));
    }

    @Test
    public void shouldSaveEditedItemTest() throws Exception {
        //WHEN
        String result = controller.saveEditedItem(item, bindingResult, redirectAttributes, LIST_ID);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(itemService, times(1)).updateItem(ITEM_ID, item.getName(), item.getComment());
    }

    @Test
    public void shouldNotSaveEditedItemIfHasErrorsTest() throws Exception {
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String result = controller.saveEditedItem(item, bindingResult, redirectAttributes, LIST_ID);
        //THEN
        assertEquals("redirect:/item/edit?id=" + ITEM_ID, result);
        verify(itemService, times(0)).updateItem(ITEM_ID, item.getName(), item.getComment());
        verify(redirectAttributes, times(1)).addFlashAttribute("org.springframework.validation.BindingResult." + EDIT_ITEM_FORM_NAME, bindingResult);
        verify(redirectAttributes, times(1)).addFlashAttribute(EDIT_ITEM_FORM_NAME, item);
        verify(redirectAttributes, times(1)).addFlashAttribute("listId", LIST_ID);
    }

    @Test
    public void shouldToggleItemBoughtStatusTest() throws Exception {
        //GIVEN
        when(item.isBought()).thenReturn(true);
        //WHEN
        String result = controller.toggleItemBoughtStatus(ITEM_ID, LIST_ID);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(itemService, times(1)).toggleBoughtStatus(ITEM_ID);
    }

    @Test
    public void shouldDeleteItemTest() throws Exception {
        //WHEN
        String result = controller.deleteItem(ITEM_ID, LIST_ID);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(itemService, times(1)).deleteItem(ITEM_ID);
    }
}