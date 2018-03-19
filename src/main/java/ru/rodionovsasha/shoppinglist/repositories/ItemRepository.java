package ru.rodionovsasha.shoppinglist.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}
