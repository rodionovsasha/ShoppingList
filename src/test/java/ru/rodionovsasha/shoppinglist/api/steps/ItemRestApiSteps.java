package ru.rodionovsasha.shoppinglist.api.steps;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rodionovsasha.shoppinglist.api.BaseApiCucumberTest;
import ru.rodionovsasha.shoppinglist.api.utils.FeignClientRequestUtils;
import ru.rodionovsasha.shoppinglist.context.SharedContext;

import static junit.framework.TestCase.assertEquals;


/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Slf4j
public class ItemRestApiSteps extends BaseApiCucumberTest implements En {
    @Autowired
    private SharedContext sharedContext;
    @Autowired
    private FeignClientRequestUtils feignClientRequestUtils;

    public ItemRestApiSteps() {
        When("^I get item json with id = (\\d+)$", (Long id) -> sharedContext.itemDto = feignClientRequestUtils.getItem(id));

        Then("^Response should contain id = (\\d+)$", (Long id) -> assertEquals(id, sharedContext.itemDto.getId()));

        Then("^Response should contain name = (.*?)$", (String name) -> assertEquals(name, sharedContext.itemDto.getName()));

    }
}