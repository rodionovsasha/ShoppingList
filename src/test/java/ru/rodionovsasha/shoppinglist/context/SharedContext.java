package ru.rodionovsasha.shoppinglist.context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Component
@CucumberSharedContext
public class SharedContext {
    public JSONObject jsonObject;
    public JSONArray jsonArray;
    public Integer responseCode;
}