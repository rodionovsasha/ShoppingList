package ru.rodionovsasha.shoppinglist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
import ru.rodionovsasha.shoppinglist.services.ItemService;
import ru.rodionovsasha.shoppinglist.services.ItemsListService;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Arrays.asList;
import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@SpringBootApplication
@EnableSwagger2
public class Application {
    public static final String API_BASE_URL = "/v1/api";

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

    @Bean
    protected Docket swaggerApiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage("ru.rodionovsasha.shoppinglist.controllers.rest"))
                .paths(ant(API_BASE_URL + "/**"))
                .build();
    }
}
