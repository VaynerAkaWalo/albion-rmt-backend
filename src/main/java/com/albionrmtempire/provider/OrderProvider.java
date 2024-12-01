package com.albionrmtempire.provider;

import com.albionrmtempire.dataobject.orders.ItemOrder;
import com.albionrmtempire.dataobject.orders.PersistedOrder;
import com.albionrmtempire.dataobject.orders.ResourceOrder;
import com.albionrmtempire.datatransferobject.preprocessedorders.ItemOrderRequest;
import com.albionrmtempire.datatransferobject.preprocessedorders.PreProcessedOrder;
import com.albionrmtempire.datatransferobject.preprocessedorders.ResourceOrderRequest;
import com.albionrmtempire.repository.ItemOrderRepository;
import com.albionrmtempire.repository.ResourceOrderRepository;
import com.vaynerakawalo.springobservability.logging.annotation.Egress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class OrderProvider {

    private final ItemOrderRepository itemOrderRepository;
    private final ResourceOrderRepository resourceOrderRepository;

    @Egress
    public Optional<? extends PersistedOrder> getOrderIfExists(PreProcessedOrder orderRequest) {
        return switch (orderRequest) {
            case ItemOrderRequest itemOrder -> itemOrderRepository.findByOrderId(itemOrder.getOrderId());
            case ResourceOrderRequest resourceOrder -> resourceOrderRepository.findByOrderId(resourceOrder.getOrderId());
            default -> Optional.empty();
        };
    }

    @Egress
    public void persistOrder(PreProcessedOrder orderRequest) {
        switch (orderRequest) {
            case ItemOrderRequest itemOrder -> itemOrderRepository.save(ItemOrder.fromDto(itemOrder));
            case ResourceOrderRequest resourceRequest -> resourceOrderRepository.save(ResourceOrder.fromDto(resourceRequest));
            default -> {}
        }
    }

    @Egress
    public List<PersistedOrder> getAllOrders() {
        return Stream.concat(
                itemOrderRepository.findAll().stream(),
                resourceOrderRepository.findAll().stream()
                )
                .collect(Collectors.toList());

    }

    @Egress
    public void updateOrder(PersistedOrder order) {
        switch (order) {
            case ItemOrder itemOrder -> itemOrderRepository.save(itemOrder);
            case ResourceOrder resourceOrder -> resourceOrderRepository.save(resourceOrder);
            default -> {}
        }
    }

    @Egress
    public void removeOrder(PersistedOrder order) {
        switch (order) {
            case ItemOrder itemOrder -> itemOrderRepository.delete(itemOrder);
            case ResourceOrder resourceOrder -> resourceOrderRepository.delete(resourceOrder);
            default -> {}
        }
    }

    @Egress
    public long ordersCount() {
        return itemOrderRepository.count() + resourceOrderRepository.count();
    }

    @Egress
    public long notFullTtlOrders() {
        final var limit = PersistedOrder.DEFAULT_TTL;
        return itemOrderRepository.countAllByTtlLessThan(limit) + resourceOrderRepository.countAllByTtlLessThan(limit);
    }

    @Egress
    public long deadOrders() {
        final var limit = 0;
        return itemOrderRepository.countAllByTtlLessThan(limit) + resourceOrderRepository.countAllByTtlLessThan(limit);
    }
}
