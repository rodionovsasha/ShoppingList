package ru.rodionovsasha.shoppinglist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

@Repository
public interface ItemsListRepository extends JpaRepository<ItemsList, Long> {
    @Query("SELECT i FROM ItemsList i WHERE i.name = :name")
    ItemsList findByName(@Param("name") String name);
}