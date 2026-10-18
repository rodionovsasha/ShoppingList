package io.github.rodionovsasha.shoppinglist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.github.rodionovsasha.shoppinglist.dto.ItemDto;
import io.github.rodionovsasha.shoppinglist.dto.ItemsListDto;
import io.github.rodionovsasha.shoppinglist.services.ItemService;
import io.github.rodionovsasha.shoppinglist.services.ItemsListService;

import static java.util.Arrays.asList;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(ItemsListService itemsListService, ItemService itemService) {
        return args -> {
            asList("Shopping list 1", "Shopping list 2", "Shopping list 3")
                    .forEach(name -> itemsListService.addItemsList(new ItemsListDto(name)));

            asList("Oranges 1kg", "Item2", "Item3", "Item1")
                    .forEach(name -> itemService.addItem(new ItemDto(1, name)));
            asList("Milk", "Apples 2kg", "Bread")
                    .forEach(name -> itemService.addItem(new ItemDto(2, name)));
            asList("Meat 2kg", "Item2")
                    .forEach(name -> itemService.addItem(new ItemDto(3, name)));
        };
    }
}
