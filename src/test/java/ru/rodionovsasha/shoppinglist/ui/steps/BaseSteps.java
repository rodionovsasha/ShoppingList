package ru.rodionovsasha.shoppinglist.ui.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import ru.rodionovsasha.shoppinglist.ui.BaseCucumberTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.Assert.assertEquals;
import static ru.rodionovsasha.shoppinglist.ui.utils.TestUtils.getTitle;
import static ru.rodionovsasha.shoppinglist.ui.utils.TestUtils.openUrl;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class BaseSteps extends BaseCucumberTest {
    @When("^I open page with url: (.*?)$")
    public void openPageByUrl(String url) {
        openUrl(url);
    }

    @Then("^I should see All lists header$")
    public void verifyOpenedAllListPage() throws Exception {
        $("div.container h1").shouldHave(text("All lists"));
    }

    @Then("^I should see a page with title=(.*?)$")
    public void verifyPageTitle(String title) {
        assertEquals(title, getTitle());
    }

    @Then("^I click on the link with text=(.*?)$")
    public void clickOnTheLinkWithText(String text) {
        $(By.linkText(text)).click();
    }

    @Then("^I click on the link with href=(.*?)$")
    public void clickOnTheLinkWithHref(String href) {
        $(By.xpath("//a[@href='" + href + "']")).click();
    }

    @Then("^I should see link with text=(.*?)$")
    public void verifyLinkWithText(String text) {
        $(By.linkText(text)).should(exist);
    }

    @Then("^I should see link with href=(.*?)$")
    public void verifyLinkWithUrl(String href) {
        $(By.xpath("//a[@href=" + href + "]"));
    }

    @Then("^I should see link with url=(.[^\\s]*?) and text=(.*?)$")
    public void verifyLinkWithTextAndHref(String href, String text) throws UnsupportedEncodingException {
        $(By.xpath("//a[contains(., '" + text + "') and contains(@href, '" + URLDecoder.decode(href, "UTF-8") + "')]")).should(exist);
    }

    @Then("^I should see header with text=(.*?)$")
    public void verifyHeader(String text) throws Exception {
        $("h1").shouldHave(text(text));
    }

    @Then("^I try to add new list with name=(.*?)$")
    public void tryToAddNewList(String name) throws Exception {
        $("#name").setValue(name);
        $(By.name("addItemsList")).submit();
    }

    @Then("^I try to update list with new name=(.*?)$")
    public void tryToUpdateList(String newName) throws Exception {
        $("#name").setValue(newName);
        $(By.name("editedItemsList")).submit();
    }

    @Then("^I should see error message=(.*?)$")
    public void verifyErrorMessage(String message) throws Exception {
        $("small.text-danger").shouldHave(text(message));
    }

    @Then("^I should not see link with url=(.[^\\s]*?)$")
    public void verifyLinkWithTextAndHref(String href) throws UnsupportedEncodingException {
        $(By.xpath("//a[contains(@href, '" + URLDecoder.decode(href, "UTF-8") + "')]")).shouldNot(exist);
    }
}