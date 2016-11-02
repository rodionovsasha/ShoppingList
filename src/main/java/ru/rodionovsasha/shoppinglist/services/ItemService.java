package ru.rodionovsasha.shoppinglist.services;

import ru.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public interface ItemService {
    Item findOneItemById(long itemId);
    Item findOneItemByName(String name);
    void saveNewItem(Item item, long listId);
    void updateItem(long itemId, String name, String comment);
    void toggleBoughtStatus(long itemId);
    void deleteItem(long itemId);
}