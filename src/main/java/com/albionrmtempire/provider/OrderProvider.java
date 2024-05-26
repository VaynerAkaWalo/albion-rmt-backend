package com.albionrmtempire.provider;

import com.albionrmtempire.dataobject.orders.ItemOrder;
import com.albionrmtempire.dataobject.orders.PersistedOrder;
import com.albionrmtempire.dataobject.orders.ResourceOrder;
import com.albionrmtempire.datatransferobject.preprocessedorders.ItemOrderRequest;
import com.albionrmtempire.datatransferobject.preprocessedorders.PreProcessedOrder;
import com.albionrmtempire.datatransferobject.preprocessedorders.ResourceOrderRequest;
import com.albionrmtempire.repository.ItemOrderRepository;
import com.albionrmtempire.repository.ResourceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderProvider {

    private final ItemOrderRepository itemOrderRepository;
    private final ResourceOrderRepository resourceOrderRepository;

    public Optional<? extends PersistedOrder> getOrderIfExists(PreProcessedOrder orderRequest) {
        return switch (orderRequest) {
            case ItemOrderRequest itemOrder -> itemOrderRepository.findById(orderRequest.getOrderId());
            case ResourceOrderRequest resourceOrder -> resourceOrderRepository.findById(resourceOrder.getOrderId());
            default -> Optional.empty();
        };
    }

    public void persistOrder(PreProcessedOrder orderRequest) {
        switch (orderRequest) {
            case ItemOrderRequest itemOrder -> itemOrderRepository.save(ItemOrder.fromDto(itemOrder));
            case ResourceOrderRequest resourceRequest -> resourceOrderRepository.save(ResourceOrder.fromDto(resourceRequest));
            default -> {return;}
        };
    }

    public long ordersCount() {
        return itemOrderRepository.count() + resourceOrderRepository.count();
    }

    public long notFullTtlOrders() {
        final var limit = PersistedOrder.DEFAULT_TTL;
        return itemOrderRepository.countAllByTtlLessThan(limit) + resourceOrderRepository.countAllByTtlLessThan(limit);
    }

    public long deadOrders() {
        final var limit = 0;
        return itemOrderRepository.countAllByTtlLessThan(limit) + resourceOrderRepository.countAllByTtlLessThan(limit);
    }
}
