package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.Item;
import com.albionrmtempire.dataobject.PersistedOrder;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PersistedOrderRepository extends CrudRepository<PersistedOrder, Long> {

    Collection<PersistedOrder> findAll();
    Collection<PersistedOrder> findAllByTtlGreaterThan(long limit);

    List<PersistedOrder> findAllByItemAndTierAndEnchantAndQualityAndOrderIdNot(
            AggregateReference<Item, String> item, short tier, short enchant, short quality, long orderId
    );

    Optional<PersistedOrder> findByOrderId(long id);

    long countAllByTtlLessThan(long limit);
}
