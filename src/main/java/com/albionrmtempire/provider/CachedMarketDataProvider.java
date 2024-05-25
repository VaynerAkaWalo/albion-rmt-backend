package com.albionrmtempire.provider;

import com.albionrmtempire.datatransferobject.OrderResponse;
import com.albionrmtempire.repository.ItemOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CachedMarketDataProvider {

    private final ItemOrderRepository itemOrderRepository;
    private final CacheableResourceProvider cacheableResourceProvider;

    @Cacheable("orders")
    public Collection<OrderResponse> getAllNotExpiredItemOrders() {
        final UnaryOperator<String> fetchDisplayName
                = systemName -> cacheableResourceProvider.getItemBySystemName(systemName).displayName();

        return itemOrderRepository.findAllByTtlGreaterThan(0)
                .stream()
                .map(order -> order.toDto(fetchDisplayName.apply(order.getSystemName())))
                .collect(Collectors.toSet());
    }


}
