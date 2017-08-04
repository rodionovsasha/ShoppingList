package ru.rodionovsasha.shoppinglist.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Entity
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
public class ItemsList {
    @Id @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy = "itemsList", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("IS_BOUGHT, NAME")
    private List<Item> items = new LinkedList<>();
}
