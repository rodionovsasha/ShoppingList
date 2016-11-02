package ru.rodionovsasha.shoppinglist.unit;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseUnitTest {
    @Mock
    BindingResult bindingResult;
    @Mock
    RedirectAttributes redirectAttributes;
    @Mock
    ItemsList itemsList;

    ModelMap modelMap;
}