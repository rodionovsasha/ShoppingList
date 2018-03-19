package ru.rodionovsasha.shoppinglist.context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;

/*
 * Copyright (©) 2017. Rodionov Aleksandr
 */

@Component
@CucumberSharedContext
public class SharedContext {
    public JSONObject jsonObject;
    public JSONArray jsonArray;
    public Integer responseCode;
    public ItemDto itemDto;
}