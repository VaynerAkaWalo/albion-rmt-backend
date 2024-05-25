package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.orders.ItemOrder;
import com.albionrmtempire.dataobject.orders.ResourceOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ResourceOrderRepository extends CrudRepository<ResourceOrder, Long> {

    long countAllByTtlLessThan(long limit);
}
