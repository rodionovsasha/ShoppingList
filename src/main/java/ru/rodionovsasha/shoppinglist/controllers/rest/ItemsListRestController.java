package ru.rodionovsasha.shoppinglist.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import java.util.List;

import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Slf4j
@Api(value = "ItemsList", description = "Items List management")
@RestController
@RequestMapping("/rest")
public class ItemsListRestController {
    private final ItemsListService itemsListService;

    @Autowired
    public ItemsListRestController(ItemsListService itemsListService) {
        this.itemsListService = itemsListService;
    }

    @ApiOperation(value = "Get all lists")
    @GetMapping("/")
    public List<ItemsList> getAllLists() {
        return itemsListService.findAllLists();
    }

    @ApiOperation(value = "Get list by id")
    @GetMapping(ITEMS_LIST_BASE_PATH)
    public ItemsList getItemsList(@RequestParam(value = "id") final long id) {
        return itemsListService.getItemsListById(id);
    }
}