package ru.rodionovsasha.shoppinglist.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.rodionovsasha.shoppinglist.Application.API_BASE_URL;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Slf4j
@Api(value = "ItemsList", description = "Items Lists management")
@RestController
@RequestMapping(API_BASE_URL)
public class ItemsListRestController {
    private final ItemsListService itemsListService;

    @Autowired
    public ItemsListRestController(ItemsListService itemsListService) {
        this.itemsListService = itemsListService;
    }

    @ApiOperation("Get all lists")
    @GetMapping
    public List<ItemsList> getAllLists() {
        return itemsListService.findAllLists();
    }

    @ApiOperation("Get list")
    @GetMapping(ITEMS_LIST_BASE_PATH + "/{id}")
    public ItemsList getItemsList(@PathVariable final long id) {
        return itemsListService.getItemsListById(id);
    }

    @ApiOperation("Add list")
    @PostMapping(value = ITEMS_LIST_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ItemsListDto saveItemsList(@Valid @RequestBody ItemsListDto itemsListDto) {
        itemsListService.addItemsList(itemsListDto);
        return itemsListDto;
    }

    @ApiOperation("Update list")
    @PutMapping(value = ITEMS_LIST_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editItemsList(@Valid @RequestBody ItemsListDto itemsListDto) {
        if (itemsListService.getItemsListById(itemsListDto.getId()) == null) {
            log.error("List with id '" + itemsListDto.getId() + "' not found");
            return ResponseEntity.notFound().build();
        }
        itemsListService.updateItemsList(itemsListDto);
        return ResponseEntity.ok(itemsListDto);
    }

    @ApiOperation("Delete list")
    @DeleteMapping(ITEMS_LIST_BASE_PATH + "/{id}")
    public ResponseEntity<?> deleteItemsList(@PathVariable final long id) {
        if (itemsListService.getItemsListById(id) == null) {
            log.error("List with id '" + id + "' not found");
            return ResponseEntity.notFound().build();
        }

        itemsListService.deleteItemsList(id);
        return ResponseEntity.ok().build();
    }
}