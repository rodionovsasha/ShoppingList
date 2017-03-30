package ru.rodionovsasha.shoppinglist.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Entity
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
        //JPA
    }

    public Item(String name, ItemsList itemsList) {
        this.name = name;
        this.itemsList = itemsList;
    }
}