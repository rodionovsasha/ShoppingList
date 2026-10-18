package io.github.rodionovsasha.shoppinglist.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import io.github.rodionovsasha.shoppinglist.services.ItemsListService;

/*
 * Copyright (©) 2017. Rodionov Aleksandr
 */

@Slf4j
@Controller
@AllArgsConstructor
public class MainController {
    private ItemsListService itemsListService;

    @GetMapping("/")
    public String getAllLists(ModelMap modelMap) {
        modelMap.addAttribute("itemsLists", itemsListService.findAllLists());
        return "index";
    }
}
