package io.github.rodionovsasha.shoppinglist.services.impl;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rodionovsasha.shoppinglist.dto.ItemsListDto;
import io.github.rodionovsasha.shoppinglist.entities.ItemsList;
import io.github.rodionovsasha.shoppinglist.exceptions.NotFoundException;
import io.github.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import io.github.rodionovsasha.shoppinglist.services.ItemsListService;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@AllArgsConstructor
@Service
@Transactional
public class ItemsListServiceImpl implements ItemsListService {
    private final ItemsListRepository itemsListRepository;

    @Override
    public long addItemsList(ItemsListDto itemsListDto) {
        return itemsListRepository.save(itemsListDto.toItemsList()).getId();
    }

    @Override
    public void updateItemsList(ItemsListDto itemsListDto) {
        var itemsList = itemsListRepository
                .findById(itemsListDto.getId())
                .orElseThrow(() -> NotFoundException.forId(itemsListDto.getId()));
        itemsListDto.toItemsList(itemsList);
        itemsListRepository.save(itemsList);
    }

    @Override
    public void deleteItemsList(long id) {
        itemsListRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemsList getItemsListById(long id) {
        return itemsListRepository.findById(id).orElseThrow(() -> NotFoundException.forId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ItemsList> findAllLists() {
        return itemsListRepository.findAll();
    }
}
