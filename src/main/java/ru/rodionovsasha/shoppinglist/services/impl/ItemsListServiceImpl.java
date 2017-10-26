package ru.rodionovsasha.shoppinglist.services.impl;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import java.util.List;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@AllArgsConstructor
@Service
@Transactional
public class ItemsListServiceImpl implements ItemsListService {
    private final ItemsListRepository itemsListRepository;

    @Override
    public long addItemsList(ItemsListDto itemsListDto) {
        return itemsListRepository.saveAndFlush(itemsListDto.toItemsList()).getId();
    }

    @Override
    public void updateItemsList(ItemsListDto itemsListDto) {
        val itemsList = itemsListRepository.findOne(itemsListDto.getId());
        itemsListDto.toItemsList(itemsList);
        itemsListRepository.saveAndFlush(itemsList);
    }

    @Override
    public void deleteItemsList(long id) {
        itemsListRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemsList getItemsListById(long id) {
        return itemsListRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemsList> findAllLists() {
        return itemsListRepository.findAll();
    }
}
