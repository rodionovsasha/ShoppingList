package ru.rodionovsasha.shoppinglist;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.repositories.ItemRepository;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;

import java.util.Collections;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TestApplicationConfiguration {
    public static final String LIST_NAME = "Shopping list name";
    public static final String ITEM_NAME = "Item1";
    public static final Long LIST_ID = 1L;
    public static final Long ITEM_ID = 1L;

    @Bean
    CommandLineRunner runner(ItemsListRepository itemsListRepository, ItemRepository itemRepository) {
        return args -> {
            Collections.singletonList(LIST_NAME)
                    .forEach(name -> itemsListRepository.save(new ItemsList(name)));
            Collections.singletonList(ITEM_NAME)
                    .forEach(name -> itemRepository.save(new Item(name, itemsListRepository.findByName(LIST_NAME))));
        };
    }
}