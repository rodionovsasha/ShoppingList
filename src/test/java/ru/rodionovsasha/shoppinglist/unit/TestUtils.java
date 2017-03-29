package ru.rodionovsasha.shoppinglist.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtils {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Cannot get json as a string: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}