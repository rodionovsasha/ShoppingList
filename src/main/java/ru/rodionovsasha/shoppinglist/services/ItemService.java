package ru.rodionovsasha.shoppinglist.services;

import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public interface ItemService {
    void addItem(ItemDto itemDto);
    void updateItem(ItemDto itemDto);
    void deleteItem(long id);
    Item getItemById(long id);
    void toggleBoughtStatus(long id);
}
