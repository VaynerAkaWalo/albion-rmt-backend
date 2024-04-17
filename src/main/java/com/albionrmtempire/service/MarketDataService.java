package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.PersistedOrder;
import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.exception.UnsupportedBuyerException;
import com.albionrmtempire.producer.OrderProducer;
import com.albionrmtempire.repository.PersistedOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Map<String, List<String>> publishOrders(List<OrderRequest> orders) {
        var result = orders.stream()
                .map(this::publishOrder)
                .collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toList())));

        logPublishedOrders(result);

        return result;
    }

    public void processOrder(PersistedOrder order) {
        Optional<PersistedOrder> existingOrder = orderRepository.findByOrderId(order.getOrderId());
        if (existingOrder.isPresent()) {
            updateOrder(existingOrder.get(), order);
            return;
        }

        reduceTtlOfOlderRequests(order);

        order.setTtl(BASE_TTL);
        orderRepository.save(order);
    }

    private void reduceTtlOfOlderRequests(PersistedOrder processedOrder) {
        List<PersistedOrder> orders
                = orderRepository.findAllByItemAndTierAndEnchantAndQualityAndOrderIdNot(processedOrder.getItem(), processedOrder.getTier(), processedOrder.getEnchant(), processedOrder.getQuality(), processedOrder.getOrderId());

        orders.forEach(order -> {
            order.setTtl(order.getTtl() - 1);
            orderRepository.save(order);
        });
    }

    private void updateOrder(PersistedOrder persistedOrder, PersistedOrder newOrder) {
        if (checkForUpdates(persistedOrder, newOrder) || persistedOrder.getTtl() != BASE_TTL) {
            persistedOrder.setAmount(newOrder.getAmount());
            persistedOrder.setUnitPrice(newOrder.getUnitPrice());
            persistedOrder.setTtl(BASE_TTL);

            orderRepository.save(persistedOrder);
        }
    }

    private boolean checkForUpdates(PersistedOrder o1, PersistedOrder o2) {
        if (o1.getAmount() != o2.getAmount()) {
            return false;
        }
        return o1.getUnitPrice() == o2.getUnitPrice();
    }


    private void logPublishedOrders(Map<String, List<String>> orders) {
        log.info("Successfully created {} orders, {} failed",
                orders.getOrDefault(SUCCESS, List.of()).size(), orders.getOrDefault(FAIL, List.of()).size());
    }

    private Pair<String, String> publishOrder(OrderRequest order) {
        try {
            return Pair.of(SUCCESS, orderProducer.publishOrderEvent(order));
        }
        catch (UnsupportedBuyerException | NotFoundException ex) {
            log.warn("Could not publish order: {}", ex.getMessage());
            return Pair.of(FAIL, dropTierPrefix(order.itemGroupType()));
        }
    }
}
