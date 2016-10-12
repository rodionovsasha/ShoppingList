package ru.rodionovsasha.shoppinglist.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Entity
public class Item {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    private String name;

    private String comment;
    private boolean isBought;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ItemsList itemsList;

    protected Item() {
    }

    public Item(String name, ItemsList itemsList) {
        this.name = name;
        this.itemsList = itemsList;
    }

    @Override
    public String toString() {
        return String.format("Item[%d, %s, %s, %s]", id, name, comment, isBought);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemsList getItemsList() {
        return itemsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public void setItemsList(ItemsList itemsList) {
        this.itemsList = itemsList;
    }
}