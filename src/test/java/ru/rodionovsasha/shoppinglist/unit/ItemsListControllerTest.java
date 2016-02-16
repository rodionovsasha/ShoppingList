package ru.rodionovsasha.shoppinglist.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rodionovsasha.shoppinglist.controllers.ItemsListController;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.LIST_ID;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.EDIT_ITEMS_LIST_FORM_NAME;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_FORM_NAME;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemsListControllerTest extends BaseUnitTest {
    @Mock
    private ItemsListService service;
    @Mock
    private ItemsList itemsList;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;

    private ModelMap modelMap;
    private ItemsListController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ItemsListController(service);
        modelMap = new ModelMap();
        when(service.findOneItemsListById(anyLong())).thenReturn(itemsList);
        when(itemsList.getId()).thenReturn(LIST_ID);
    }

    @Test
    public void itemsListsTest() throws Exception {
        //WHEN
        String result = controller.itemsLists(modelMap);
        //THEN
        assertEquals("index", result);
        assertTrue(modelMap.containsKey("itemsLists"));
        verify(service, times(1)).findAllItemsLists();
    }

    @Test
    public void shouldFindItemsListTest() throws Exception {
        //WHEN
        String result = controller.findItemsList(LIST_ID, modelMap);
        //THEN
        assertEquals("itemsList", result);
        assertTrue(modelMap.containsKey("itemsList"));
        verify(service, times(1)).findOneItemsListById(LIST_ID);
    }

    @Test
    public void shouldShowAddItemsListFormTest() throws Exception {
        //WHEN
        String result = controller.showAddItemsListForm(modelMap);
        //THEN
        assertEquals("addItemsList", result);
        assertTrue(modelMap.containsKey(ITEMS_LIST_FORM_NAME));
    }

    @Test
    public void shouldSaveItemsListTest() throws Exception {
        //WHEN
        String result = controller.saveItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(service, times(1)).saveItemsList(itemsList);
    }

    @Test
    public void shouldNotSaveItemsListIfHasErrorsTest() throws Exception {
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String result = controller.saveItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList/add", result);
        verify(service, times(0)).saveItemsList(itemsList);
        verify(redirectAttributes, times(1)).addFlashAttribute("org.springframework.validation.BindingResult." + ITEMS_LIST_FORM_NAME, bindingResult);
        verify(redirectAttributes, times(1)).addFlashAttribute(ITEMS_LIST_FORM_NAME, itemsList);
    }

    @Test
    public void shouldShowEditItemsListFormTest() throws Exception {
        //WHEN
        String result = controller.showEditItemsListForm(LIST_ID, modelMap);
        //THEN
        assertEquals("editItemsList", result);
        assertTrue(modelMap.containsKey(EDIT_ITEMS_LIST_FORM_NAME));
    }

    @Test
    public void shouldSaveEditedItemsListTest() throws Exception {
        //WHEN
        String result = controller.saveEditedItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList?id=" + LIST_ID, result);
        verify(service, times(1)).updateItemsList(LIST_ID, itemsList.getName());
    }

    @Test
    public void shouldNotSaveEditedItemsListIfHasErrorsTest() throws Exception {
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String result = controller.saveEditedItemsList(itemsList, bindingResult, redirectAttributes);
        //THEN
        assertEquals("redirect:/itemsList/edit?id=" + LIST_ID, result);
        verify(service, times(0)).updateItemsList(LIST_ID, itemsList.getName());
        verify(redirectAttributes, times(1)).addFlashAttribute("org.springframework.validation.BindingResult." + EDIT_ITEMS_LIST_FORM_NAME, bindingResult);
        verify(redirectAttributes, times(1)).addFlashAttribute(EDIT_ITEMS_LIST_FORM_NAME, itemsList);
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