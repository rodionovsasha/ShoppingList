package ru.rodionovsasha.shoppinglist.ui.utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class WebDriverProvider {
    private static final String PHANTOM_JS_PATH = "src/test/resources/phantomjs-2.1.1/bin/phantomjs";
    private static final String PHANTOM_JS_WIN_PATH = "src\\test\\resources\\phantomjs-2.1.1\\bin\\phantomjs.exe";
    private static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
    static WebDriver webDriver;

    public static WebDriver buildWebDriver() {
        System.out.println("\033[35mBuild web driver\033[0m");
        System.out.println("\033[35mOperating system: " + OPERATING_SYSTEM + "\033[0m");
        Configuration.timeout = 8000;
        Configuration.reportsFolder = "target/reports";
        webDriver = new PhantomJSDriver(getCapabilities());
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        webDriver.manage().window().setSize(new Dimension(1280, 1024));
        return webDriver;
    }

    public static void closeWebDriver() {
        if (webDriver != null) {
            System.out.println("\033[36mClose web driver\033[0m");
            webDriver.quit();
        }
    }

    private static DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("takesScreenshot", true);
        if (OPERATING_SYSTEM.contains("win")) {
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOM_JS_WIN_PATH);
        } else {
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOM_JS_PATH);
        }
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, getCliArgs());
        return capabilities;
    }

    private static ArrayList<String> getCliArgs() {
        ArrayList<String> cliArgs = new ArrayList<>();
        cliArgs.add("--web-security=false");
        cliArgs.add("--ssl-protocol=any");
        cliArgs.add("--ignore-ssl-errors=true");
        cliArgs.add("--webdriver-loglevel=NONE");
        return cliArgs;
    }
}