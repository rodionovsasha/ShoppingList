package ru.rodionovsasha.shoppinglist.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.services.ItemService;

import javax.validation.Valid;

import static ru.rodionovsasha.shoppinglist.Utils.redirectToUrl;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Slf4j
@Controller
public class ItemController {
    public static final String ITEM_BASE_PATH = "/item";
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(ITEM_BASE_PATH)
    public String getItem(@RequestParam(value = "id") final long id, ModelMap modelMap) {
        modelMap.addAttribute("item", itemService.getItemById(id));
        return "item";
    }

    @GetMapping(ITEM_BASE_PATH + "/add")
    public String addItemForm(final ItemDto itemDto) {
        return "addItem";
    }

    @PostMapping(ITEM_BASE_PATH + "/add")
    public String saveItem(@Valid final ItemDto itemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Creating a new item has errors. Redirect back to creating page.");
            return "addItem";
        }

        itemService.addItem(itemDto);
        log.debug("Item with name " + itemDto.getName() + " has been added.");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + itemDto.getListId());
    }

    @GetMapping(ITEM_BASE_PATH + "/edit")
    public String showEditItemForm(@RequestParam(value = "id") final long id, ModelMap modelMap) {
        val item = itemService.getItemById(id);
        modelMap.addAttribute("itemDto", item);
        modelMap.addAttribute("listId", item.getItemsList().getId());
        return "editItem";
    }

    @PostMapping(ITEM_BASE_PATH + "/edit")
    public String saveEditedItem(@Valid final ItemDto itemDto, BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            log.error("Editing item has errors. Redirect back to editing page.");
            modelMap.addAttribute("listId", itemDto.getListId());
            return "editItem";
        }

        itemService.updateItem(itemDto);
        log.debug("Save edited item");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + itemDto.getListId());
    }

    @GetMapping(ITEM_BASE_PATH + "/bought")
    public String toggleItemBoughtStatus(@RequestParam(value = "id") final long id,
            @RequestParam(value = "listId") final long listId) {
        itemService.toggleBoughtStatus(id);
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + listId);
    }

    @GetMapping(ITEM_BASE_PATH + "/delete")
    public String deleteItem(@RequestParam(value = "id") final long id,
            @RequestParam(value = "listId") final long listId) {
        itemService.deleteItem(id);
        log.debug("Item with id = " + id + " has been removed");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + listId);
    }
}
