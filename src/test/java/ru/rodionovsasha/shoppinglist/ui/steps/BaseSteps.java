package ru.rodionovsasha.shoppinglist.ui.steps;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.rodionovsasha.shoppinglist.ui.BaseCucumberTest;
import ru.rodionovsasha.shoppinglist.ui.utils.TestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.Assert.assertEquals;
import static ru.rodionovsasha.shoppinglist.ui.utils.TestUtils.getTitle;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@Slf4j
public class BaseSteps extends BaseCucumberTest implements En {
    public BaseSteps() {
        When("^I open page with url: (.*?)$", TestUtils::openUrl);

        When("^I should see Shopping List header$", () -> $("div.container h1").shouldHave(text("Shopping List")));

        Then("^I should see a page with title=(.*?)$", (String title) -> assertEquals(title, getTitle()));

        Then("^I click on the link with text=(.*?)$", (String text) -> $(By.linkText(text)).click());

        Then("^I click on the link with href=(.*?)$", (String href) -> $(By.xpath("//a[@href='" + href + "']")).click());

        Then("^I should see link with text=(.*?)$", (String text) -> $(By.linkText(text)).should(exist));

        Then("^I should see link with href=(.*?)$", (String href) -> $(By.xpath("//a[@href=" + href + "]")));

        Then("^I should see link with url=(.[^\\s]*?) and text=(.*?)$", (String href, String text) -> {
            try {
                $(By.xpath("//a[contains(., '" + text + "') and contains(@href, '" + URLDecoder.decode(href, "UTF-8") + "')]")).should(exist);
            } catch (UnsupportedEncodingException e) {
                log.warn("UnsupportedEncodingException:", e);
            }
        });

        Then("^I should see header with text=(.*?)$", (String text) -> $("h1").shouldHave(text(text)));

        Then("^I try to add new list with name=(.*?)$", (String name) -> {
            $("#name").setValue(name);
            $(By.className("form-horizontal")).submit();
        });

        Then("^I try to update list with new name=(.*?)$", (String newName) -> {
            $("#name").setValue(newName);
            $(By.className("form-horizontal")).submit();
        });

        Then("^I should see error message=(.*?)$", (String message) -> $("small.text-danger").shouldHave(text(message)));

        Then("^I should not see link with url=(.[^\\s]*?)$", (String href) -> {
            try {
                $(By.xpath("//a[contains(@href, '" + URLDecoder.decode(href, "UTF-8") + "')]")).shouldNot(exist);
            } catch (UnsupportedEncodingException e) {
                log.warn("UnsupportedEncodingException:", e);
            }
        });
    }
}