package ru.rodionovsasha.shoppinglist.integration;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.rodionovsasha.shoppinglist.TestApplicationConfiguration;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@SpringBootTest
@Transactional
public abstract class BaseIntegrationTest {
    @Autowired
    WebApplicationContext webApplicationContext;
}