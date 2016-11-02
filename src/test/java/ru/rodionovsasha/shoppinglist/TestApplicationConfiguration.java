package ru.rodionovsasha.shoppinglist;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.services.ItemService;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;

import java.util.Collections;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@TestPropertySource(locations = "classpath:/test.properties")
public class TestApplicationConfiguration {
    public static final String LIST_NAME = "Shopping list name";
    public static final String ITEM_NAME = "Item1";
    public static final long LIST_ID = 1;
    public static final long ITEM_ID = 1;

    @Bean
    CommandLineRunner runner(ItemsListService itemsListService, ItemService itemService) {
        return args -> {
            Collections.singletonList(LIST_NAME)
                    .forEach(name -> itemsListService.saveItemsList(new ItemsList(name)));

            Collections.singletonList(ITEM_NAME)
                    .forEach(name -> itemService.saveNewItem(new Item(name, itemsListService.findOneItemsListById(1)), 1));
        };
    }
}