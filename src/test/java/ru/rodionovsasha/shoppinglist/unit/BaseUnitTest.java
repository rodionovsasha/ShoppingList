package ru.rodionovsasha.shoppinglist.unit;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.rodionovsasha.shoppinglist.TestApplicationConfiguration;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplicationConfiguration.class)
public abstract class BaseUnitTest {
}