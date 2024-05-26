package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.orders.ItemOrder;
import com.albionrmtempire.dataobject.orders.ResourceOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ResourceOrderRepository extends CrudRepository<ResourceOrder, Long> {

    long countAllByTtlLessThan(long limit);

    List<ResourceOrder> findAllBySystemName(String systemName);

    List<ResourceOrder> findAllBySystemNameAndSessionId(String systemName, String sessionId);
}
