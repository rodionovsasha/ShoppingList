package ru.rodionovsasha.shoppinglist.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.rodionovsasha.shoppinglist.controllers.ItemsListController.ITEMS_LIST_BASE_PATH;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Slf4j
@Api(value = "ItemsList", description = "Items List management")
@RestController
@RequestMapping("/v1/api")
public class ItemsListRestController {
    private final ItemsListService itemsListService;

    @Autowired
    public ItemsListRestController(ItemsListService itemsListService) {
        this.itemsListService = itemsListService;
    }

    @ApiOperation(value = "Get all lists")
    @GetMapping
    public List<ItemsList> getAllLists() {
        return itemsListService.findAllLists();
    }

    @ApiOperation(value = "Get list by id")
    @GetMapping(ITEMS_LIST_BASE_PATH)
    public ItemsList getItemsList(@RequestParam(value = "id") final long id) {
        return itemsListService.getItemsListById(id);
    }

    @ApiOperation(value = "Add new list")
    @PostMapping(value = ITEMS_LIST_BASE_PATH + "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemsListDto> saveItemsList(@Valid @RequestBody ItemsListDto itemsListDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(itemsListDto);
        }
        itemsListService.addItemsList(itemsListDto);
        return new ResponseEntity<>(itemsListDto, HttpStatus.OK);
    }
}