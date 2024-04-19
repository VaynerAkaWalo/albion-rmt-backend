package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.PersistedOrder;
import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.datatransferobject.OrderResponse;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.exception.UnsupportedBuyerException;
import com.albionrmtempire.producer.OrderProducer;
import com.albionrmtempire.repository.ItemRepository;
import com.albionrmtempire.repository.PersistedOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;

import static com.albionrmtempire.util.ItemUtil.dropTierPrefix;

@Service
@Log4j2
@RequiredArgsConstructor
public class MarketDataService {

    private static final String SUCCESS = "Succeed";
    private static final String FAIL = "Failed";
    private static final long BASE_TTL = 20;

    private final OrderProducer orderProducer;
    private final PersistedOrderRepository orderRepository;
    private final ItemInfoService itemInfoService;
    private final Clock clock;

    public Map<String, List<String>> publishOrders(List<OrderRequest> orders) {
        var result = orders.stream()
                .map(this::publishOrder)
                .collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toList())));

        logPublishedOrders(result);

        return result;
    }

    @Cacheable("orders")
    public Collection<OrderResponse> getAllNotExpiredOrders() {
        return orderRepository.findAllByTtlGreaterThan(0)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public void processOrder(PersistedOrder order) {
        var optionalOrder = orderRepository.findByOrderId(order.getOrderId());

        final PersistedOrder processedOrder
                = optionalOrder.map(persistedOrder -> updateExistingOrder(persistedOrder, order)).orElse(order);

        reduceTtlOfOlderRequests(processedOrder);

        saveOrder(processedOrder);
    }

    public void pseudoDeleteOrder(long orderId) {
        var existingOrder = orderRepository.findByOrderId(orderId);

        existingOrder.ifPresent(order -> {
            order.setTtl(0);
            orderRepository.save(order);
        });
    }

    private PersistedOrder updateExistingOrder(PersistedOrder existingOrder, PersistedOrder newOrder) {
        existingOrder.setAmount(newOrder.getAmount());
        existingOrder.setUnitPrice(newOrder.getUnitPrice());
        return existingOrder;
    }

    private void reduceTtlOfOlderRequests(PersistedOrder processedOrder) {
        orderRepository
                .findAllByItemAndTierAndEnchantAndQualityAndOrderIdNot(processedOrder.getItem(), processedOrder.getTier(), processedOrder.getEnchant(), processedOrder.getQuality(), processedOrder.getOrderId())
                .forEach(order -> {
                    order.setTtl(order.getTtl() - 1);
                    orderRepository.save(order);
                });
    }

    private void saveOrder(PersistedOrder order) {
        order.setTtl(BASE_TTL);
        order.setLastUpdate(Date.from(clock.instant()));
        orderRepository.save(order);
    }

    private void logPublishedOrders(Map<String, List<String>> orders) {
        log.info("Successfully created {} orders, {} failed",
                orders.getOrDefault(SUCCESS, List.of()).size(), orders.getOrDefault(FAIL, List.of()).size());
    }

    private Pair<String, String> publishOrder(OrderRequest order) {
        try {
            return Pair.of(SUCCESS, orderProducer.publishOrderEvent(order));
        } catch (UnsupportedBuyerException | NotFoundException ex) {
            log.warn("Could not publish order: {}", ex.getMessage());
            return Pair.of(FAIL, dropTierPrefix(order.itemGroupType()));
        }
    }

    public OrderResponse toDto(PersistedOrder order) {
        var item = itemInfoService.getById(order.getItem().getId());
        return new OrderResponse(
                item.systemName(),
                StringUtils.isEmpty(item.displayName()) ? item.systemName() : item.displayName(),
                order.getOrderId(),
                order.getAmount(),
                order.getUnitPrice(),
                order.getTier(),
                order.getEnchant(),
                order.getQuality(),
                order.getLastUpdate());

    }
}
