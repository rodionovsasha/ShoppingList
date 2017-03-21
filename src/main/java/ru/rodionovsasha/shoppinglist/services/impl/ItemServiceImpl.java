package ru.rodionovsasha.shoppinglist.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.repositories.ItemRepository;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import ru.rodionovsasha.shoppinglist.services.ItemService;

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
    @Transactional
    public void addItem(ItemDto itemDto) {
        Item item = itemDto.toItem();
        item.setItemsList(itemsListRepository.findOne(itemDto.getListId()));
        itemRepository.saveAndFlush(item);
    }

    @Override
    @Transactional
    public void updateItem(ItemDto itemDto) {
        Item item = itemRepository.findOne(itemDto.getId());
        itemDto.toItem(item);
        itemRepository.saveAndFlush(item);
    }

    @Override
    @Transactional
    public void deleteItem(long id) {
        itemRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemById(long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    @Transactional
    public void toggleBoughtStatus(long itemId) {
        Item item = getItemById(itemId);
        item.setBought(!item.isBought());
        itemRepository.saveAndFlush(item);
    }
}