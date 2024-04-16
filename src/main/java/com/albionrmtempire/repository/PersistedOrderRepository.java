package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.PersistedOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersistedOrderRepository extends CrudRepository<PersistedOrder, String> {

    Collection<PersistedOrder> findAll();
}
