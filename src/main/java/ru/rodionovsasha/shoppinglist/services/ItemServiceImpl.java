package ru.rodionovsasha.shoppinglist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.repositories.ItemRepository;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemsListRepository itemsListRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemsListRepository itemsListRepository) {
        this.itemRepository = itemRepository;
        this.itemsListRepository = itemsListRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Item findOneItemById(long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findOneItemByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    @Transactional
    public void saveNewItem(Item item, long listId) {
        item.setItemsList(itemsListRepository.findOne(listId));
        itemRepository.saveAndFlush(item);
    }

    @Override
    @Transactional
    public void updateItem(long itemId, String name, String comment) {
        Item item = findOneItemById(itemId);
        item.setName(name);
        item.setComment(comment);
        itemRepository.saveAndFlush(item);
    }

    @Override
    @Transactional
    public void toggleBoughtStatus(long itemId) {
        Item item = findOneItemById(itemId);
        item.setBought(!item.isBought());
        itemRepository.saveAndFlush(item);
    }

    @Override
    @Transactional
    public void deleteItem(long itemId) {
        itemRepository.delete(itemId);
    }
}