package ru.rodionovsasha.shoppinglist.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Collection;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Entity
public class ItemsList {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "itemsList", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("IS_BOUGHT, NAME")
    private Collection<Item> items;

    protected ItemsList() {
    }

    public ItemsList(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("ItemsList[%d, %s]", id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }
}