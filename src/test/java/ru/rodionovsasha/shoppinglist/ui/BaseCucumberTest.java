package ru.rodionovsasha.shoppinglist.ui;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.TestApplicationConfiguration;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static ru.rodionovsasha.shoppinglist.TestApplicationConfiguration.printTestsResultLocation;
import static ru.rodionovsasha.shoppinglist.ui.utils.WebDriverProvider.buildWebDriver;
import static ru.rodionovsasha.shoppinglist.ui.utils.WebDriverProvider.closeWebDriver;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/ui"},
        glue = {"ru.rodionovsasha.shoppinglist.ui.steps"},
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-json/cucumber.json"},
        strict = true,
        snippets = SnippetType.CAMELCASE,
        tags = {"~@ignore"})
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@SpringBootTest(webEnvironment= DEFINED_PORT)
@TestPropertySource(locations = "classpath:test.yml")
@Transactional
@ComponentScan("ru.rodionovsasha.shoppinglist.ui.steps")
public class BaseCucumberTest {
    @BeforeClass
    public static void setUp() {
        setWebDriver(buildWebDriver());
    }

    @AfterClass
    public static void tearDown() {
        closeWebDriver();
        printTestsResultLocation("target/cucumber");
    }
}
