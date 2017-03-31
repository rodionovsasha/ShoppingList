package ru.rodionovsasha.shoppinglist.controllers.react;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/react")
public class ListController {
    @GetMapping
    public String getAllLists() {
        return "react";
    }
}
