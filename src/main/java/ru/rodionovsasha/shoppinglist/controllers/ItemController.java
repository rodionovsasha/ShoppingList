package ru.rodionovsasha.shoppinglist.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.services.ItemService;

import javax.validation.Valid;

import static ru.rodionovsasha.shoppinglist.Utils.redirectToUrl;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Controller
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    public static final String ITEM_FORM_NAME = "item";
    public static final String EDIT_ITEM_FORM_NAME = "editedItem";
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String getItem(@RequestParam(value = "id") long itemId, ModelMap modelMap) {
        modelMap.put("item", itemService.findOneItemById(itemId));
        return "item";
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.GET)
    public String addItem(@RequestParam(value = "listId") long listId, ModelMap modelMap) {
        if (!modelMap.containsAttribute(ITEM_FORM_NAME)){
            LOGGER.info("Create new item");
            modelMap.put(ITEM_FORM_NAME, new Item("", null));
            modelMap.put("listId", listId);
        }
        return "addItem";
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    public String saveItem(@Valid @ModelAttribute(ITEM_FORM_NAME) Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam(value = "listId") long listId) {
        if (bindingResult.hasErrors()) {
            LOGGER.info("Creating a new item has errors. Redirect back to creating page.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + ITEM_FORM_NAME, bindingResult);
            redirectAttributes.addFlashAttribute(ITEM_FORM_NAME, item);
            redirectAttributes.addFlashAttribute("listId", listId);
            redirectAttributes.addAttribute("listId", listId);
            return redirectToUrl("/item/add");
        }

        LOGGER.info("Save new item");
        itemService.saveNewItem(item, listId);
        return redirectToUrl("/itemsList?id=" + listId);
    }

    @RequestMapping(value = "/item/edit", method = RequestMethod.GET)
    public String showEditItemForm(@RequestParam(value = "id") long itemId, ModelMap modelMap) {
        if (!modelMap.containsAttribute(EDIT_ITEM_FORM_NAME)){
            modelMap.put(EDIT_ITEM_FORM_NAME, itemService.findOneItemById(itemId));
        }
        return "editItem";
    }

    @RequestMapping(value = "/item/edit", method = RequestMethod.POST)
    public String saveEditedItem(@Valid @ModelAttribute(EDIT_ITEM_FORM_NAME) Item editedItem, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam(value = "listId") long listId) {
        long itemId = editedItem.getId();

        if (bindingResult.hasErrors()) {
            LOGGER.info("Editing item has errors. Redirect back to editing page.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + EDIT_ITEM_FORM_NAME, bindingResult);
            redirectAttributes.addFlashAttribute(EDIT_ITEM_FORM_NAME, editedItem);
            redirectAttributes.addFlashAttribute("listId", listId);
            return redirectToUrl("/item/edit?id=" + itemId);
        }

        LOGGER.info("Save edited item");
        itemService.updateItem(itemId, editedItem.getName(), editedItem.getComment());
        return redirectToUrl("/itemsList?id=" + listId);
    }

    @RequestMapping(value = "item/bought", method = RequestMethod.GET)
    public String toggleItemBoughtStatus(@RequestParam(value = "id") long itemId, @RequestParam(value = "listId") long listId) {
        itemService.toggleBoughtStatus(itemId);
        return redirectToUrl("/itemsList?id=" + listId);
    }

    @RequestMapping(value = "item/delete", method = RequestMethod.GET)
    public String deleteItem(@RequestParam(value = "id") long itemId, @RequestParam(value = "listId") long listId) {
        LOGGER.info("Item with id = " + itemId + " has been removed");
        itemService.deleteItem(itemId);
        return redirectToUrl("/itemsList?id=" + listId);
    }
}