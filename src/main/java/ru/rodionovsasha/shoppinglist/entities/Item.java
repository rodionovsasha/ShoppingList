package ru.rodionovsasha.shoppinglist.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@Entity
@Getter @Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String comment;
    private boolean isBought;

    @ManyToOne @JoinColumn
    private ItemsList itemsList;

    public Item(String name, ItemsList itemsList) {
        this.name = name;
        this.itemsList = itemsList;
    }
}
