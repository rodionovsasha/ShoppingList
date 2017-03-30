package ru.rodionovsasha.shoppinglist.api.steps;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rodionovsasha.shoppinglist.api.BaseApiCucumberTest;
import ru.rodionovsasha.shoppinglist.api.utils.HttpClientRequestUtils;
import ru.rodionovsasha.shoppinglist.context.SharedContext;

import static org.junit.Assert.assertEquals;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printColoredOutput;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Slf4j
public class ItemsListRestApiSteps extends BaseApiCucumberTest implements En {
    @Autowired
    private SharedContext sharedContext;
    @Autowired
    private HttpClientRequestUtils httpClientRequestUtils;

    public ItemsListRestApiSteps() {
        Given("^I read json array from url: (.*?)$", (String url) -> sharedContext.jsonArray = httpClientRequestUtils.readJsonArrayFromUrl(url));

        Given("^I read json object from url: (.*?)$", (String url) -> sharedContext.jsonObject = httpClientRequestUtils.readJsonObjectFromUrl(url));

        Given("^I send a DELETE request to (.*?)$", (String url) -> sharedContext.responseCode = httpClientRequestUtils.executeDelete(url));

        When("^I send a POST request to (.*?) with the following:$", (String url, String json) -> sharedContext.jsonObject = httpClientRequestUtils
                .executePost(url, json));

        When("^I send a PUT request to (.*?) with the following:$", (String url, String json) -> sharedContext.jsonObject = httpClientRequestUtils
                .executePut(url, json));

        Then("^Response should contain JSON array:$", (String expectedJsonString) -> {
            JSONArray jsonResponse = sharedContext.jsonArray;
            printColoredOutput("JSON response array: " + jsonResponse);
            JSONAssert.assertEquals(new JSONArray(expectedJsonString), jsonResponse, JSONCompareMode.LENIENT);
        });

        Then("^Response should contain JSON object:$", (String expectedJsonString) -> {
            JSONObject jsonResponse = sharedContext.jsonObject;
            printColoredOutput("JSON response object: " + jsonResponse);
            JSONAssert.assertEquals(new JSONObject(expectedJsonString), jsonResponse, JSONCompareMode.LENIENT);
        });

        Then("^Response code should be (\\d+)$", (Integer responseCode) -> assertEquals(responseCode, sharedContext.responseCode));
    }
}