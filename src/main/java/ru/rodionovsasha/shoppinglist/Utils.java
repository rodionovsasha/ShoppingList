package ru.rodionovsasha.shoppinglist;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static String redirectToReferer(HttpServletRequest request) {
        return redirectToUrl(request.getHeader("Referer"));
    }

    public static String redirectToUrl(String url) {
        return "redirect:" + url;
    }
}