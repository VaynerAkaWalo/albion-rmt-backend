package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.orders.ItemOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ItemOrderRepository extends CrudRepository<ItemOrder, Long> {

    Collection<ItemOrder> findAllByTtlGreaterThan(long limit);

    long countAllByTtlLessThan(long limit);
}
