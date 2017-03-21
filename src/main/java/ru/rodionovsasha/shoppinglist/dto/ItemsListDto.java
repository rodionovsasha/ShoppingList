package ru.rodionovsasha.shoppinglist.dto;

/*
 * Copyright (©) 2017. Rodionov Alexander
 */

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;

import java.io.Serializable;

@Getter @Setter
public class ItemsListDto implements Serializable {
    private long id;
    @NotEmpty
    private String name;

    public ItemsListDto() {
    }

    public ItemsListDto(String name) {
        this.name = name;
    }

    public ItemsList toItemsList() {
        return toItemsList(new ItemsList());
    }

    public ItemsList toItemsList(ItemsList itemsList) {
        itemsList.setName(this.name);
        return itemsList;
    }
}
