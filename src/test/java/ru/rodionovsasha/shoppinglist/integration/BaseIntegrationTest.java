package ru.rodionovsasha.shoppinglist.integration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionovsasha.shoppinglist.TestApplicationConfiguration;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:/test.properties")
public abstract class BaseIntegrationTest {
}