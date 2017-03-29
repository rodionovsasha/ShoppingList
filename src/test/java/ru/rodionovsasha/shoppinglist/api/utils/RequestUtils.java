package ru.rodionovsasha.shoppinglist.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printColoredOutput;

@Component
@Slf4j
public class RequestUtils {
    private static final String TEST_HOST = "http://localhost:8001";

    @Autowired
    private HttpClientProvider httpClientProvider;

    public JSONObject readJsonObjectFromUrl(String url) {
        printColoredOutput("Read JSON object from url: " +  url);
        return new JSONObject(executeGet( TEST_HOST + url));
    }

    public JSONArray readJsonArrayFromUrl(String url) {
        printColoredOutput("Read JSON array from url: " + url);
        return new JSONArray(executeGet(TEST_HOST + url));
    }

    public JSONObject executePost(String url, String json) {
        HttpPost httpPost = new HttpPost(TEST_HOST + url);
        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        httpPost.setHeader(ACCEPT, APPLICATION_JSON_VALUE);
        httpPost.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        httpPost.setEntity(entity);

        printColoredOutput("Post json to url: " + url);
        return new JSONObject(httpClientProvider.executeRequest(httpPost));
    }

    public JSONObject executePut(String url, String json) {
        HttpPut httpPut = new HttpPut(TEST_HOST + url);
        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        httpPut.setHeader(ACCEPT, APPLICATION_JSON_VALUE);
        httpPut.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        httpPut.setEntity(entity);

        printColoredOutput("Put json to url: " + url);
        return new JSONObject(httpClientProvider.executeRequest(httpPut));
    }

    public Integer executeDelete(String url) {
        return httpClientProvider.executeRequestAndGetStatusCode(new HttpDelete(TEST_HOST + url));
    }

    private String executeGet(String url) {
        return httpClientProvider.executeRequest(new HttpGet(url));
    }
}