package ru.rodionovsasha.shoppinglist.integration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.TestApplicationConfiguration;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplicationConfiguration.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
@TestPropertySource(locations = "classpath:/test.properties")
public abstract class BaseIntegrationTest {
}