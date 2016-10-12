package ru.rodionovsasha.shoppinglist.services;

import org.springframework.stereotype.Service;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;

import java.util.List;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Service
public interface ItemsListService {
    List<ItemsList> findAllItemsLists();
    ItemsList findOneItemsListById(long listId);
    ItemsList findOneItemsListByName(String name);
    void saveItemsList(ItemsList itemsList);
    void updateItemsList(long listId, String name);
    void deleteItemsList(long listId);
}