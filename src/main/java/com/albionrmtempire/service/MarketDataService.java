package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.orders.PersistedOrder;
import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.datatransferobject.preprocessedorders.PreProcessedOrder;
import com.albionrmtempire.exception.MalformedOrderRequestException;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.exception.UnsupportedOrderException;
import com.albionrmtempire.producer.OrderProducer;
import com.albionrmtempire.provider.OrderProvider;
import com.albionrmtempire.repository.ItemOrderRepository;
import com.albionrmtempire.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class MarketDataService {

    private static final String SUCCESS = "Succeed";
    private static final String FAIL = "Failed";

    private final OrderProducer orderProducer;
    private final ItemOrderRepository itemOrderRepository;
    private final OrderProvider orderProvider;

    public Map<String, List<String>> publishOrdersRequestEvents(List<OrderRequest> orders) {
        var result = orders.stream()
                .map(this::publishOrder)
                .collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toList())));

        logPublishedOrders(result);

        return result;
    }

    public void processOrderRequest(PreProcessedOrder orderRequest) {
        final var existingOrder = orderProvider.getOrderIfExists(orderRequest);

        if (existingOrder.isPresent()) {
            existingOrder.get().updateOrder(orderRequest);
        } else {
            orderProvider.persistOrder(orderRequest);
        }
    }

    public void pseudoDeleteOrder(long orderId) {
        var existingOrder = itemOrderRepository.findById(orderId);

        existingOrder.ifPresent(order -> {
            order.setTtl(0);
            itemOrderRepository.save(order);
        });
    }

    public void adjustOffersTtl() {
        final var orders = orderProvider.getAllOrders();

        for (PersistedOrder order : orders) {
            order.setTtl(order.getTtl() - 1);

            if (order.getTtl() > 0) {
                orderProvider.updateOrder(order);
            } else {
                orderProvider.removeOrder(order);
            }
        }
    }

    private void logPublishedOrders(Map<String, List<String>> orders) {
        log.info("Successfully created {} orders, {} failed",
                orders.getOrDefault(SUCCESS, List.of()).size(), orders.getOrDefault(FAIL, List.of()).size());
    }

    private Pair<String, String> publishOrder(OrderRequest order) {
        try {
            return Pair.of(SUCCESS, orderProducer.publishOrderEvent(order));
        } catch (MalformedOrderRequestException | UnsupportedOrderException | NotFoundException ex) {
            log.warn("Could not publish order: {}", ex.getMessage());
            return Pair.of(FAIL, ItemUtil.getItemSystemName(order.itemGroupType()));
        }
    }
}
