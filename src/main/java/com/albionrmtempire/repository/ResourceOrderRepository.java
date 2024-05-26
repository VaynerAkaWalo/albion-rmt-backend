package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.orders.ResourceOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceOrderRepository extends CrudRepository<ResourceOrder, Long> {

    List<ResourceOrder> findAll();

    Optional<ResourceOrder> findByOrderId(long orderId);

    long countAllByTtlLessThan(long limit);

    List<ResourceOrder> findAllBySystemName(String systemName);

    List<ResourceOrder> findAllBySystemNameAndSessionId(String systemName, String sessionId);
}
