package ru.rodionovsasha.shoppinglist.controllers.rest;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.services.ItemService;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.rodionovsasha.shoppinglist.Application.API_BASE_URL;
import static ru.rodionovsasha.shoppinglist.controllers.ItemController.ITEM_BASE_PATH;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Slf4j
@RestController
@RequestMapping(API_BASE_URL)
public class ItemRestController {
    private final ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation("Get item")
    @GetMapping(value = ITEM_BASE_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public Item getItem(@PathVariable final long id) {
        return itemService.getItemById(id);
    }

    @ApiOperation("Add item")
    @PostMapping(value = ITEM_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> saveItem(@Valid @RequestBody ItemDto itemDto) {
        itemService.addItem(itemDto);
        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
    }

    @ApiOperation("Update item")
    @PutMapping(value = ITEM_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity editItem(@Valid @RequestBody ItemDto itemDto) {
        if (itemService.getItemById(itemDto.getId()) == null) {
            log.error("Item with id '" + itemDto.getId() + "' not found");
            return ResponseEntity.notFound().build();
        }
        itemService.updateItem(itemDto);
        return ResponseEntity.ok(itemDto);
    }

    @ApiOperation("Delete item")
    @DeleteMapping(ITEM_BASE_PATH + "/{id}")
    public ResponseEntity deleteItem(@PathVariable final long id) {
        if (itemService.getItemById(id) == null) {
            log.error("Item with id " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}