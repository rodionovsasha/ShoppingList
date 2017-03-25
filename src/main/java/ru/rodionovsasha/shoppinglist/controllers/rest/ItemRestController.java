package ru.rodionovsasha.shoppinglist.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
@Api(value = "Item", description = "Items management")
@RestController
@RequestMapping(API_BASE_URL)
public class ItemRestController {
    private final ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "Get item")
    @GetMapping(ITEM_BASE_PATH + "/{id}")
    public Item getItem(@PathVariable final long id) {
        return itemService.getItemById(id);
    }

    @ApiOperation(value = "Add item")
    @PostMapping(value = ITEM_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> saveItem(@Valid @RequestBody ItemDto ItemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Cannot add new item due errors: ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.error("Field '" + error.getField() + "' : " + error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(ItemDto);
        }
        itemService.addItem(ItemDto);
        return new ResponseEntity<>(ItemDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Update item")
    @PutMapping(value = ITEM_BASE_PATH + "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> editItem(@PathVariable final long id, @Valid @RequestBody ItemDto itemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Cannot update item due errors: ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.error("Field '" + error.getField() + "' : " + error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(itemDto);
        }

        Item item = itemService.getItemById(id);
        if (item == null) {
            log.error("Item with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        itemDto.setId(id);
        itemDto.setListId(item.getItemsList().getId());

        itemService.updateItem(itemDto);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete item")
    @DeleteMapping(value = ITEM_BASE_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> deleteItem(@PathVariable final long id) {
        if (itemService.getItemById(id) == null) {
            log.error("Item with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}