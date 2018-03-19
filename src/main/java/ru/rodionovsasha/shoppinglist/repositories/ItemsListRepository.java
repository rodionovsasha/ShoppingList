package ru.rodionovsasha.shoppinglist.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rodionovsasha.shoppinglist.entities.ItemsList;

/*
 * Copyright (©) 2016. Rodionov Aleksandr
 */

@Repository
public interface ItemsListRepository extends CrudRepository<ItemsList, Long> {
}
