package ru.rodionovsasha.shoppinglist.dto;

/*
 * Copyright (©) 2017. Rodionov Aleksandr
 */

import lombok.Getter;
import lombok.Setter;

import ru.rodionovsasha.shoppinglist.entities.Item;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class ItemDto implements Serializable {
    private static final long serialVersionUID = 1;

    private Long id;
    private long listId;
    @NotEmpty
    private String name;
    private String comment;

    public ItemDto() {
    }

    public ItemDto(long listId, String name) {
        this.listId = listId;
        this.name = name;
    }

    public Item toItem() {
        return toItem(new Item());
    }

    public Item toItem(Item item) {
        item.setName(this.name);
        item.setComment(this.comment);
        return item;
    }
}
