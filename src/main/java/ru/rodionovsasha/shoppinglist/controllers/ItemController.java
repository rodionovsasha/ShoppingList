package ru.rodionovsasha.shoppinglist.controllers;

import lombok.extern.slf4j.Slf4j;
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

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Slf4j
@Controller
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item")
    public String getItem(@RequestParam(value = "id") final long id, ModelMap modelMap) {
        modelMap.addAttribute("item", itemService.getItemById(id));
        return "item";
    }

    @GetMapping("/item/add")
    public String addItemForm(final ItemDto itemDto) {
        return "addItem";
    }

    @PostMapping("/item/add")
    public String saveItem(@Valid final ItemDto itemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Creating a new item has errors. Redirect back to creating page.");
            return "addItem";
        }

        itemService.addItem(itemDto);
        log.info("Item with name " + itemDto.getName() + " has been added.");
        return redirectToUrl("/itemsList?id=" + itemDto.getListId());
    }

    @GetMapping("/item/edit")
    public String showEditItemForm(@RequestParam(value = "id") final long id, ModelMap modelMap) {
        modelMap.addAttribute("itemDto", itemService.getItemById(id));
        return "editItem";
    }

    @PostMapping("/item/edit")
    public String saveEditedItem(@Valid final ItemDto itemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Editing item has errors. Redirect back to editing page.");
            return "editItem";
        }

        itemService.updateItem(itemDto);
        log.info("Save edited item");
        return redirectToUrl("/itemsList?id=" + itemDto.getListId());
    }

    @GetMapping("item/bought")
    public String toggleItemBoughtStatus(@RequestParam(value = "id") final long id, @RequestParam(value = "listId") final long listId) {
        itemService.toggleBoughtStatus(id);
        return redirectToUrl("/itemsList?id=" + listId);
    }

    @GetMapping("item/delete")
    public String deleteItem(@RequestParam(value = "id") final long id, @RequestParam(value = "listId") final long listId) {
        itemService.deleteItem(id);
        log.info("Item with id = " + id + " has been removed");
        return redirectToUrl("/itemsList?id=" + listId);
    }
}