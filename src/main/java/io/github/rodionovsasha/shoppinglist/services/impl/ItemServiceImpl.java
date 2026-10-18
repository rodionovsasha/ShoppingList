package io.github.rodionovsasha.shoppinglist.services.impl;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rodionovsasha.shoppinglist.dto.ItemDto;
import io.github.rodionovsasha.shoppinglist.entities.Item;
import io.github.rodionovsasha.shoppinglist.exceptions.NotFoundException;
import io.github.rodionovsasha.shoppinglist.repositories.ItemRepository;
import io.github.rodionovsasha.shoppinglist.repositories.ItemsListRepository;
import io.github.rodionovsasha.shoppinglist.services.ItemService;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@AllArgsConstructor
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemsListRepository itemsListRepository;

    @Override
    public void addItem(ItemDto itemDto) {
        var item = itemDto.toItem();
        item.setItemsList(itemsListRepository.findById(itemDto.getListId())
                .orElseThrow(() -> NotFoundException.forId(itemDto.getListId()))
        );
        itemRepository.save(item);
    }

    @Override
    public void updateItem(ItemDto itemDto) {
        var item = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> NotFoundException.forId(itemDto.getId()));
        itemDto.toItem(item);
        itemRepository.save(item);
    }

    @Override
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> NotFoundException.forId(itemId));
    }

    @Override
    public void toggleBoughtStatus(long itemId) {
        var item = itemRepository.findById(itemId).orElseThrow(() -> NotFoundException.forId(itemId));
        item.setBought(!item.isBought());
        itemRepository.save(item);
    }
}
