package io.github.rodionovsasha.shoppinglist.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.github.rodionovsasha.shoppinglist.entities.Item;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}
