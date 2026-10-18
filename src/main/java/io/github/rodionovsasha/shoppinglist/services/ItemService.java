package io.github.rodionovsasha.shoppinglist.services;

import io.github.rodionovsasha.shoppinglist.dto.ItemDto;
import io.github.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

public interface ItemService {
    void addItem(ItemDto itemDto);

    void updateItem(ItemDto itemDto);

    void deleteItem(long id);

    Item getItemById(long id);

    void toggleBoughtStatus(long id);
}
