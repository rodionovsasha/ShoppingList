package ru.rodionovsasha.shoppinglist.controllers.rest;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import javax.validation.Valid;

import static ru.rodionovsasha.shoppinglist.Application.API_BASE_URL;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(API_BASE_URL)
public class ItemsListRestController {
    private final ItemsListService itemsListService;

    @ApiOperation("Get all lists")
    @GetMapping
    public Iterable<ItemsList> getAllLists() {
        return itemsListService.findAllLists();
    }

    @ApiOperation("Get list")
    @GetMapping(ITEMS_LIST_BASE_PATH + "/{id}")
    public ItemsList getItemsList(@PathVariable final long id) {
        return itemsListService.getItemsListById(id);
    }

    @ApiOperation("Add list")
    @PostMapping(ITEMS_LIST_BASE_PATH)
    public ResponseEntity<ItemsListDto> saveItemsList(@Valid @RequestBody ItemsListDto itemsListDto) {
        itemsListService.addItemsList(itemsListDto);
        return new ResponseEntity<>(itemsListDto, HttpStatus.CREATED);
    }

    @ApiOperation("Update list")
    @PutMapping(ITEMS_LIST_BASE_PATH)
    public ResponseEntity editItemsList(@Valid @RequestBody ItemsListDto itemsListDto) {
        if (itemsListService.getItemsListById(itemsListDto.getId()) == null) {
            log.error("List with id '" + itemsListDto.getId() + "' not found");
            return ResponseEntity.notFound().build();
        }
        itemsListService.updateItemsList(itemsListDto);
        return ResponseEntity.ok(itemsListDto);
    }

    @ApiOperation("Delete list")
    @DeleteMapping(ITEMS_LIST_BASE_PATH + "/{id}")
    public ResponseEntity deleteItemsList(@PathVariable final long id) {
        if (itemsListService.getItemsListById(id) == null) {
            log.error("List with id '" + id + "' not found");
            return ResponseEntity.notFound().build();
        }

        itemsListService.deleteItemsList(id);
        return ResponseEntity.noContent().build();
    }
}
