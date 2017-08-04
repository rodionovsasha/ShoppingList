package ru.rodionovsasha.shoppinglist.services;

import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;

import java.util.List;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public interface ItemsListService {
    long addItemsList(ItemsListDto itemsListDto);
    void updateItemsList(ItemsListDto itemsListDto);
    void deleteItemsList(long id);
    ItemsList getItemsListById(long id);
    List<ItemsList> findAllLists();
}
