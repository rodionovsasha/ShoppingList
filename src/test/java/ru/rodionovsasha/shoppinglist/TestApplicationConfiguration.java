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
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;
import ru.rodionovsasha.shoppinglist.dto.ItemsListDto;
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
    public static final long ITEM_ID = 1;
    public static final String ITEM_ID_PARAM = Long.toString(ITEM_ID);
    public static final long LIST_ID = 1;
    public static final String LIST_ID_PARAM = Long.toString(LIST_ID);

    @Bean
    CommandLineRunner runner(ItemsListService itemsListService, ItemService itemService) {
        return args -> {
            Collections.singletonList(LIST_NAME)
                    .forEach(name -> itemsListService.addItemsList(new ItemsListDto(name)));

            Collections.singletonList(ITEM_NAME)
                    .forEach(name -> itemService.addItem(new ItemDto(LIST_ID, name)));
        };
    }

    public static InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
}