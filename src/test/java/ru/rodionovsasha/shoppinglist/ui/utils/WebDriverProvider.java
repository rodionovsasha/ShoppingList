package ru.rodionovsasha.shoppinglist.ui.utils;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printColoredOutput;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

public class WebDriverProvider {
    static WebDriver webDriver;

    public static WebDriver buildWebDriver() {
        printColoredOutput("Building Chrome web driver...");
        WebDriverManager.chromedriver().setup();

        Configuration.timeout = 8000;
        Configuration.reportsFolder = "target/reports";

        webDriver = new ChromeDriver(getChromeOptions());
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        webDriver.manage().window().setSize(new Dimension(1280, 1024));
        return webDriver;
    }

    public static void closeWebDriver() {
        if (webDriver != null) {
            printColoredOutput("Closing web driver...");
            webDriver.quit();
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless", "window-size=1280,1024");
        return chromeOptions;
    }
}
