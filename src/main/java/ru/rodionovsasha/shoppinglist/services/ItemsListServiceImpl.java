package ru.rodionovsasha.shoppinglist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;

import java.util.List;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Service
public class ItemsListServiceImpl implements ItemsListService {
    private final ItemsListRepository itemsListRepository;

    @Autowired
    public ItemsListServiceImpl(ItemsListRepository itemsListRepository) {
        this.itemsListRepository = itemsListRepository;
    }

    @Override
    @Transactional
    public List<ItemsList> findAllItemsLists() {
        return itemsListRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemsList findOneItemsListById(long listId) {
        return itemsListRepository.findOne(listId);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemsList findOneItemsListByName(String name) {
        return itemsListRepository.findByName(name);
    }

    @Override
    @Transactional
    public void saveItemsList(ItemsList itemsList) {
        itemsListRepository.saveAndFlush(itemsList);
    }

    @Override
    @Transactional
    public void updateItemsList(long listId, String name) {
        ItemsList itemsList = findOneItemsListById(listId);
        itemsList.setName(name);
        itemsListRepository.saveAndFlush(itemsList);
    }

    @Override
    @Transactional
    public void deleteItemsList(long listId) {
        itemsListRepository.delete(listId);
    }
}