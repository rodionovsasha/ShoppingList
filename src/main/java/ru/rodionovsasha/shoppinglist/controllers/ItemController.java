package ru.rodionovsasha.shoppinglist.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/item")
    public String getItem(@RequestParam(value = "id") final long itemId, ModelMap modelMap) {
        modelMap.put("item", itemService.findOneItemById(itemId));
        return "item";
    }

    @GetMapping(value = "/item/add")
    public String addItem(@RequestParam(value = "listId") final long listId, ModelMap modelMap) {
        if (!modelMap.containsAttribute(ITEM_FORM_NAME)){
            LOGGER.info("Create new item");
            modelMap.put(ITEM_FORM_NAME, new Item("", null));
            modelMap.put("listId", listId);
        }
        return "addItem";
    }

    @PostMapping(value = "/item/add")
    public String saveItem(@Valid @ModelAttribute(ITEM_FORM_NAME) final Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam(value = "listId") final long listId) {
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

    @GetMapping(value = "/item/edit")
    public String showEditItemForm(@RequestParam(value = "id") final long itemId, ModelMap modelMap) {
        if (!modelMap.containsAttribute(EDIT_ITEM_FORM_NAME)){
            modelMap.put(EDIT_ITEM_FORM_NAME, itemService.findOneItemById(itemId));
        }
        return "editItem";
    }

    @PostMapping(value = "/item/edit")
    public String saveEditedItem(@Valid @ModelAttribute(EDIT_ITEM_FORM_NAME) final Item editedItem, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam(value = "listId") final long listId) {
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

    @GetMapping(value = "item/bought")
    public String toggleItemBoughtStatus(@RequestParam(value = "id") final long itemId, @RequestParam(value = "listId") final long listId) {
        itemService.toggleBoughtStatus(itemId);
        return redirectToUrl("/itemsList?id=" + listId);
    }

    @GetMapping(value = "item/delete")
    public String deleteItem(@RequestParam(value = "id") final long itemId, @RequestParam(value = "listId") final long listId) {
        LOGGER.info("Item with id = " + itemId + " has been removed");
        itemService.deleteItem(itemId);
        return redirectToUrl("/itemsList?id=" + listId);
    }
}