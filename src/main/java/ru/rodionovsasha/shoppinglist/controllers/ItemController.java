package ru.rodionovsasha.shoppinglist.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.services.ItemService;

import javax.validation.Valid;

import static ru.rodionovsasha.shoppinglist.Utils.redirectToUrl;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/item")
public class ItemController {
    public static final String ITEM_BASE_PATH = "/item";
    private ItemService itemService;

    @GetMapping
    public String getItem(@RequestParam("id") long id, ModelMap modelMap) {
        modelMap.addAttribute("item", itemService.getItemById(id));
        return "item";
    }

    @GetMapping("/add")
    public String addItemForm(ItemDto itemDto) {
        return "addItem";
    }

    @PostMapping("/add")
    public String saveItem(@Valid ItemDto itemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Creating a new item has errors. Redirect back to creating page.");
            return "addItem";
        }

        itemService.addItem(itemDto);
        log.debug("Item with name " + itemDto.getName() + " has been added.");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + itemDto.getListId());
    }

    @GetMapping("/edit")
    public String showEditItemForm(@RequestParam("id") long id, ModelMap modelMap) {
        val item = itemService.getItemById(id);
        modelMap.addAttribute("itemDto", item);
        modelMap.addAttribute("listId", item.getItemsList().getId());
        return "editItem";
    }

    @PostMapping("/edit")
    public String saveEditedItem(@Valid ItemDto itemDto, BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            log.error("Editing item has errors. Redirect back to editing page.");
            modelMap.addAttribute("listId", itemDto.getListId());
            return "editItem";
        }

        itemService.updateItem(itemDto);
        log.debug("Save edited item");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + itemDto.getListId());
    }

    @GetMapping("/bought")
    public String toggleItemBoughtStatus(@RequestParam("id") long id, @RequestParam("listId") long listId) {
        itemService.toggleBoughtStatus(id);
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + listId);
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam("id") long id, @RequestParam("listId") long listId) {
        itemService.deleteItem(id);
        log.debug("Item with id = " + id + " has been removed");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + listId);
    }
}
