package ru.rodionovsasha.shoppinglist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.name = :name")
    Item findByName(@Param("name") String name);
}