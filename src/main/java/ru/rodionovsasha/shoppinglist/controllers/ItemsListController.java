package ru.rodionovsasha.shoppinglist.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import javax.validation.Valid;

import static ru.rodionovsasha.shoppinglist.Utils.redirectToUrl;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Controller
public class ItemsListController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsListController.class);
    public static final String ITEMS_LIST_FORM_NAME = "itemsList";
    public static final String EDIT_ITEMS_LIST_FORM_NAME = "editedItemsList";

    private final ItemsListService itemsListService;

    @Autowired
    public ItemsListController(ItemsListService itemsListService) {
        this.itemsListService = itemsListService;
    }

    @RequestMapping("/")
    public String itemsLists(ModelMap modelMap) {
        modelMap.put("itemsLists", itemsListService.findAllItemsLists());
        return "index";
    }

    @RequestMapping(value = "/itemsList", method = RequestMethod.GET)
    public String findItemsList(@RequestParam(value = "id") final Long listId, ModelMap modelMap) {
        LOGGER.debug("Open items list with listId = " + listId);
        modelMap.put("itemsList", itemsListService.findOneItemsListById(listId));
        return "itemsList";
    }

    @RequestMapping(value = "/itemsList/add", method = RequestMethod.GET)
    public String showAddItemsListForm(ModelMap modelMap) {
        if (!modelMap.containsAttribute(ITEMS_LIST_FORM_NAME)){
            LOGGER.info("Create new items list");
            modelMap.put(ITEMS_LIST_FORM_NAME, new ItemsList(""));
        }
        return "addItemsList";
    }

    @RequestMapping(value = "/itemsList/add", method = RequestMethod.POST)
    public String saveItemsList(@Valid @ModelAttribute(ITEMS_LIST_FORM_NAME) ItemsList itemsList, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (itemsListService.findOneItemsListByName(itemsList.getName()) != null) {
            bindingResult.addError(new FieldError(ITEMS_LIST_FORM_NAME, "name", "Shopping list with name '" + itemsList.getName() + "' already exists"));
        }

        if (bindingResult.hasErrors()) {
            LOGGER.info("Creating a new items list has errors. Redirect back to creating page.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + ITEMS_LIST_FORM_NAME, bindingResult);
            redirectAttributes.addFlashAttribute(ITEMS_LIST_FORM_NAME, itemsList);
            return redirectToUrl("/itemsList/add");
        }

        LOGGER.info("Save new items list");
        itemsListService.saveItemsList(itemsList);
        return redirectToUrl("/itemsList?id=" + itemsList.getId());
    }

    @RequestMapping(value = "/itemsList/edit", method = RequestMethod.GET)
    public String showEditItemsListForm(@RequestParam(value = "id") final Long listId, ModelMap modelMap) {
        if (!modelMap.containsAttribute(EDIT_ITEMS_LIST_FORM_NAME)){
            modelMap.put(EDIT_ITEMS_LIST_FORM_NAME, itemsListService.findOneItemsListById(listId));
        }
        return "editItemsList";
    }

    @RequestMapping(value = "/itemsList/edit", method = RequestMethod.POST)
    public String saveEditedItemsList(@Valid @ModelAttribute(EDIT_ITEMS_LIST_FORM_NAME) ItemsList editedItemsList, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Long listId = editedItemsList.getId();

        if (bindingResult.hasErrors()) {
            LOGGER.info("Editing items list has errors. Redirect back to editing page.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + EDIT_ITEMS_LIST_FORM_NAME, bindingResult);
            redirectAttributes.addFlashAttribute(EDIT_ITEMS_LIST_FORM_NAME, editedItemsList);
            return redirectToUrl("/itemsList/edit?id=" + listId);
        }

        LOGGER.info("Save edited items list");
        itemsListService.updateItemsList(listId, editedItemsList.getName());
        return redirectToUrl("/itemsList?id=" + listId);
    }

    @RequestMapping(value = "itemsList/delete", method = RequestMethod.GET)
    public String deleteItemsList(@RequestParam(value = "id") final Long itemsListId) {
        LOGGER.info("Items list with id = " + itemsListId + " has been removed");
        itemsListService.deleteItemsList(itemsListId);
        return redirectToUrl("/");
    }
}