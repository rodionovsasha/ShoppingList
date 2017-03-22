package ru.rodionovsasha.shoppinglist.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String comment;
    private boolean isBought;
    @ManyToOne
    @JoinColumn
    private ItemsList itemsList;

    public Item() {
    }

    public Item(String name, ItemsList itemsList) {
        this.name = name;
        this.itemsList = itemsList;
    }
}