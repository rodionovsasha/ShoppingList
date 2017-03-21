package ru.rodionovsasha.shoppinglist.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.ModelMap;
import ru.rodionovsasha.shoppinglist.controllers.ItemsListController;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_ID;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemsListControllerTest extends BaseUnitTest {
    @Mock
    private ItemsListService service;

    private ItemsListController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ItemsListController(service);
        modelMap = new ModelMap();
        when(service.getItemsListById(anyLong())).thenReturn(itemsList);
        when(itemsList.getId()).thenReturn(LIST_ID);
    }

    @Test
    public void itemsListsTest() throws Exception {
        //WHEN
        String result = controller.getAllLists(modelMap);
        //THEN
        assertEquals("index", result);
        assertTrue(modelMap.containsKey("getAllLists"));
        verify(service, times(1)).findAllLists();
    }

    @Test
    public void shouldFindItemsListTest() throws Exception {
        //WHEN
        String result = controller.getItemsList(LIST_ID, modelMap);
        //THEN
        assertEquals("itemsList", result);
        assertTrue(modelMap.containsKey("itemsList"));
        verify(service, times(1)).getItemsListById(LIST_ID);
    }

    @Test
    public void shouldShowAddItemsListFormTest() throws Exception {
        //WHEN
      /*  String result = controller.addItemsListForm(modelMap);
        //THEN
        assertEquals("addItemsList", result);
        assertTrue(modelMap.containsKey(ITEMS_LIST_FORM_NAME));*/
    }

    @Test
    public void shouldSaveItemsListTest() throws Exception {
        //WHEN
       /* String result = controller.saveItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(service, times(1)).addItemsList(itemsList);*/
    }

    @Test
    public void shouldNotSaveItemsListIfHasErrorsTest() throws Exception {
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        /*String result = controller.saveItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList/add", result);
        verify(service, times(0)).addItemsList(itemsList);
        verify(redirectAttributes, times(1)).addFlashAttribute("org.springframework.validation.BindingResult." + ITEMS_LIST_FORM_NAME, bindingResult);
        verify(redirectAttributes, times(1)).addFlashAttribute(ITEMS_LIST_FORM_NAME, itemsList);*/
    }

    @Test
    public void shouldShowEditItemsListFormTest() throws Exception {
        //WHEN
        String result = controller.showEditItemsListForm(LIST_ID, modelMap);
        //THEN
       // assertEquals("editItemsList", result);
        //assertTrue(modelMap.containsKey(EDIT_ITEMS_LIST_FORM_NAME));
    }

    @Test
    public void shouldSaveEditedItemsListTest() throws Exception {
        //WHEN
/*        String result = controller.saveEditedItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(service, times(1)).updateItemsList(LIST_ID, itemsList.getName());*/
    }

    @Test
    public void shouldNotSaveEditedItemsListIfHasErrorsTest() throws Exception {
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
/*        String result = controller.saveEditedItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList/edit?id=" + LIST_ID, result);
        verify(service, times(0)).updateItemsList(LIST_ID, itemsList.getName());
        verify(redirectAttributes, times(1)).addFlashAttribute("org.springframework.validation.BindingResult." + EDIT_ITEMS_LIST_FORM_NAME, bindingResult);
        verify(redirectAttributes, times(1)).addFlashAttribute(EDIT_ITEMS_LIST_FORM_NAME, itemsList);*/
    }

    @Test
    public void shouldDeleteItemsListTest() throws Exception {
        //WHEN
        String result = controller.deleteItemsList(LIST_ID);
        //THEN
        assertEquals("redirect:/", result);
        verify(service, times(1)).deleteItemsList(LIST_ID);
    }
}