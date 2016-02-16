package ru.rodionovsasha.shoppinglist.services;

import org.springframework.stereotype.Service;
import ru.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Service
public interface ItemService {
    Item findOneItemById(Long itemId);
    Item findOneItemByName(String name);
    void saveNewItem(Item item, Long listId);
    void updateItem(Long itemId, String name, String comment);
    void toggleBoughtStatus(Long itemId);
    void deleteItem(Long itemId);
}