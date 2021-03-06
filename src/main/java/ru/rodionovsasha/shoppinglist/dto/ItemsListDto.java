package ru.rodionovsasha.shoppinglist.dto;

/*
 * Copyright (©) 2017. Rodionov Aleksandr
 */

import lombok.Getter;
import lombok.Setter;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter @Setter
public class ItemsListDto implements Serializable {
    private static final long serialVersionUID = 1;

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
