package io.github.rodionovsasha.shoppinglist.services;

import io.github.rodionovsasha.shoppinglist.dto.ItemsListDto;
import io.github.rodionovsasha.shoppinglist.entities.ItemsList;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

public interface ItemsListService {
    long addItemsList(ItemsListDto itemsListDto);

    void updateItemsList(ItemsListDto itemsListDto);

    void deleteItemsList(long id);

    ItemsList getItemsListById(long id);

    Iterable<ItemsList> findAllLists();
}
