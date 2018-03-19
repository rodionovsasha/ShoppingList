package ru.rodionovsasha.shoppinglist.ui.utils;

import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.open;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printColoredOutput;
import static ru.rodionovsasha.shoppinglist.ui.utils.WebDriverProvider.webDriver;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@Slf4j
public class TestUtils {
    private static final String TEST_HOST = "http://localhost:8001";

    public static void openUrl(String url){
        String fullUrl = TEST_HOST + url;
        printColoredOutput("Open url: " + fullUrl);
        open(fullUrl);
    }

    public static String getTitle() {
        return webDriver.getTitle();
    }
}