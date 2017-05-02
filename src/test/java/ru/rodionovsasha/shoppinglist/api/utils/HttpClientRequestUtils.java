package ru.rodionovsasha.shoppinglist.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printColoredOutput;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

@Component
@Slf4j
public class HttpClientRequestUtils {
    @Value("${api.test.url}")
    private String apiTestUrl;

    @Autowired
    private HttpClientProvider httpClientProvider;

    public JSONObject readJsonObjectFromUrl(String url) {
        printColoredOutput("Read JSON object from url: " + apiTestUrl + url);
        try {
            return new JSONObject(executeGet( apiTestUrl + url));
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public JSONArray readJsonArrayFromUrl(String url) {
        printColoredOutput("Read JSON array from url: " + apiTestUrl + url);
        try {
            return new JSONArray(executeGet(apiTestUrl + url));
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public JSONObject executePost(String url, String json) {
        HttpPost httpPost = new HttpPost(apiTestUrl + url);
        StringEntity entity;
        try {
            entity = new StringEntity(json);
            httpPost.setHeader(ACCEPT, APPLICATION_JSON_VALUE);
            httpPost.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
            httpPost.setEntity(entity);

            printColoredOutput("Post json to url: " + apiTestUrl + url);
            return new JSONObject(httpClientProvider.executeRequest(httpPost));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public JSONObject executePut(String url, String json) {
        HttpPut httpPut = new HttpPut(apiTestUrl + url);
        StringEntity entity;
        try {
            entity = new StringEntity(json);
            httpPut.setHeader(ACCEPT, APPLICATION_JSON_VALUE);
            httpPut.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
            httpPut.setEntity(entity);

            printColoredOutput("Put json to url: " + apiTestUrl + url);
            return new JSONObject(httpClientProvider.executeRequest(httpPut));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Integer executeDelete(String url) {
        return httpClientProvider.executeRequestAndGetStatusCode(new HttpDelete(apiTestUrl + url));
    }

    private String executeGet(String url) {
        return httpClientProvider.executeRequest(new HttpGet(url));
    }
}