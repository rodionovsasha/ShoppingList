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
    public List<ItemsList> findAllItemsLists() {
        return itemsListRepository.findAll();
    }

    @Override
    public ItemsList findOneItemsListById(Long listId) {
        return itemsListRepository.findOne(listId);
    }

    @Override
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
    public void updateItemsList(Long listId, String name) {
        ItemsList itemsList = findOneItemsListById(listId);
        itemsList.setName(name);
        saveItemsList(itemsList);
    }

    @Override
    @Transactional
    public void deleteItemsList(Long listId) {
        itemsListRepository.delete(listId);
    }
}