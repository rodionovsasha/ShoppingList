package ru.rodionovsasha.shoppinglist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.conditionalcomments.dialect.ConditionalCommentsDialect;
import ru.rodionovsasha.shoppinglist.entities.Item;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;
import ru.rodionovsasha.shoppinglist.repositories.ItemRepository;
import ru.rodionovsasha.shoppinglist.repositories.ItemsListRepository;

import static java.util.Arrays.asList;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(ItemsListRepository itemsListRepository, ItemRepository itemRepository) {
        return args -> {
            asList("Shopping list 1", "Shopping list 2", "Shopping list 3")
                    .forEach(name -> itemsListRepository.save(new ItemsList(name)));
            asList("Oranges 1kg", "Item2", "Item3", "Item1")
                    .forEach(name -> itemRepository.save(new Item(name, itemsListRepository.findByName("Shopping list 1"))));
            asList("Milk", "Apples 2kg", "Bread")
                    .forEach(name -> itemRepository.save(new Item(name, itemsListRepository.findByName("Shopping list 2"))));
            asList("Meat 2kg", "Item2")
                    .forEach(name -> itemRepository.save(new Item(name, itemsListRepository.findByName("Shopping list 3"))));
        };
    }

    @Bean
    public ConditionalCommentsDialect conditionalCommentDialect() {
        return new ConditionalCommentsDialect();
    }
}