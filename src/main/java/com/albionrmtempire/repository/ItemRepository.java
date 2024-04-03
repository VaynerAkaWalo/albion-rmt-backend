package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {}
