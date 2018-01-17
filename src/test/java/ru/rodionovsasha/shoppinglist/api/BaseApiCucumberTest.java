package ru.rodionovsasha.shoppinglist.api;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.rodionovsasha.shoppinglist.TestApplicationConfiguration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printCucumberTestsResultLocation;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printTestsResultLocation;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/api"},
        glue = {"ru.rodionovsasha.shoppinglist.api.steps"},
        plugin = {"pretty", "html:target/cucumber-api", "json:target/cucumber-json/cucumber-api.json"},
        strict = true,
        snippets = SnippetType.CAMELCASE,
        tags = {"~@ignore"})
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@SpringBootTest(webEnvironment= DEFINED_PORT)
@TestPropertySource(locations = "classpath:test.yml")
@ComponentScan("ru.rodionovsasha.shoppinglist.api.steps")
public class BaseApiCucumberTest {
    @AfterClass
    public static void tearDown() {
        printCucumberTestsResultLocation();
        printTestsResultLocation("target/cucumber-api");
    }
}