package ru.rodionovsasha.shoppinglist.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import javax.validation.Valid;

import static ru.rodionovsasha.shoppinglist.Utils.redirectToUrl;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Slf4j
@Controller
public class ItemsListController {
    public static final String ITEMS_LIST_BASE_PATH = "/itemsList";
    private final ItemsListService itemsListService;

    @Autowired
    public ItemsListController(ItemsListService itemsListService) {
        this.itemsListService = itemsListService;
    }

    @GetMapping("/")
    public String getAllLists(ModelMap modelMap) {
        modelMap.addAttribute("itemsLists", itemsListService.findAllLists());
        return "index";
    }

    @GetMapping(ITEMS_LIST_BASE_PATH)
    public String getItemsList(@RequestParam(value = "id") final long id, ModelMap modelMap) {
        modelMap.addAttribute("itemsList", itemsListService.getItemsListById(id));
        return "itemsList";
    }

    @GetMapping(ITEMS_LIST_BASE_PATH + "/add")
    public String addItemsListForm(final ItemsListDto itemsListDto) {
        return "addItemsList";
    }

    @PostMapping(ITEMS_LIST_BASE_PATH + "/add")
    public String saveItemsList(@Valid final ItemsListDto itemsListDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Creating a new items list has errors. Redirect back to creating page.");
            return "addItemsList";
        }

        long listId = itemsListService.addItemsList(itemsListDto);
        log.debug("Item list with name " + itemsListDto.getName() + " has been added.");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + listId);
    }

    @GetMapping(ITEMS_LIST_BASE_PATH + "/edit")
    public String showEditItemsListForm(@RequestParam(value = "id") final long id, ModelMap modelMap) {
        modelMap.addAttribute("itemsListDto", itemsListService.getItemsListById(id));
        return "editItemsList";
    }

    @PostMapping(ITEMS_LIST_BASE_PATH + "/edit")
    public String saveEditedItemsList(@Valid final ItemsListDto itemsListDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Editing items list has errors. Redirect back to editing page.");
            return "editItemsList";
        }

        itemsListService.updateItemsList(itemsListDto);
        log.debug("Save edited items list");
        return redirectToUrl(ITEMS_LIST_BASE_PATH + "?id=" + itemsListDto.getId());
    }

    @GetMapping("itemsList/delete")
    public String deleteItemsList(@RequestParam(value = "id") final long id) {
        itemsListService.deleteItemsList(id);
        log.debug("Items list with id = " + id + " has been removed");
        return redirectToUrl("/");
    }
}
