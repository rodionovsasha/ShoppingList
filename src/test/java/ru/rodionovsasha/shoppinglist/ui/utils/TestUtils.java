package ru.rodionovsasha.shoppinglist.ui.utils;

import static com.codeborne.selenide.Selenide.open;
import static ru.rodionovsasha.shoppinglist.ui.utils.WebDriverProvider.webDriver;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class TestUtils {
    private static final String TEST_HOST = "http://localhost:8001";

    public static void openUrl(String url){
        String fullUrl = TEST_HOST + url;
        System.out.println("\033[35mOpen url: " + fullUrl + "\033[0m");
        open(fullUrl);
    }

    public static String getTitle() {
        return webDriver.getTitle();
    }
}