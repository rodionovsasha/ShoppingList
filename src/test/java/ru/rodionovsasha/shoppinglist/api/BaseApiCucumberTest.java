package ru.rodionovsasha.shoppinglist.api;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.TestApplicationConfiguration;

import java.io.File;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printColoredOutput;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/api"},
        glue = {"ru.rodionovsasha.shoppinglist.api.steps"},
        plugin = {"pretty", "html:target/cucumber-api", "json:target/cucumber-api.json"},
        strict = true,
        snippets = SnippetType.CAMELCASE,
        tags = {"~@ignore"})
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@SpringBootTest(webEnvironment= DEFINED_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
public class BaseApiCucumberTest {
    @AfterClass
    public static void tearDown() {
        printColoredOutput("Report location: " + new File("target/cucumber-api").getAbsolutePath() + "/index.html");
    }
}