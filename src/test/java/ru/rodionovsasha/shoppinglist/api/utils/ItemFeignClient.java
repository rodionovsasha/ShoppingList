package ru.rodionovsasha.shoppinglist.api.utils;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

public interface ItemFeignClient {
    String ITEM_API_URL = "/v1/api/item";

    @RequestLine("GET " + ITEM_API_URL + "/{id}")
    ItemDto getItem(@Param("id") Long id);

    @RequestLine("POST " + ITEM_API_URL)
    @Headers(CONTENT_TYPE + ": " + APPLICATION_JSON_VALUE)
    String saveItem(ItemDto itemDto);

    @RequestLine("PUT " + ITEM_API_URL)
    @Headers(CONTENT_TYPE + ": " + APPLICATION_JSON_VALUE)
    String editItem(ItemDto itemDto);

    @RequestLine("DELETE " + ITEM_API_URL + "/{id}")
    void deleteItem(@Param("id") Integer id);
}
