package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.orders.ItemOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemOrderRepository extends CrudRepository<ItemOrder, Long> {

    List<ItemOrder> findAll();

    Optional<ItemOrder> findByOrderId(long orderId);

    Collection<ItemOrder> findAllByTtlGreaterThan(long limit);

    long countAllByTtlLessThan(long limit);
}
