package ru.rodionovsasha.shoppinglist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);
}