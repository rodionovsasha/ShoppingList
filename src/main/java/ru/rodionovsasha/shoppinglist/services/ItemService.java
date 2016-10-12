package ru.rodionovsasha.shoppinglist.services;

import org.springframework.stereotype.Service;
import ru.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Service
public interface ItemService {
    Item findOneItemById(long itemId);
    Item findOneItemByName(String name);
    void saveNewItem(Item item, long listId);
    void updateItem(long itemId, String name, String comment);
    void toggleBoughtStatus(long itemId);
    void deleteItem(long itemId);
}