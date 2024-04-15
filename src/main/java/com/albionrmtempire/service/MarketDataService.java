package com.albionrmtempire.service;

import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.exception.UnsupportedBuyerException;
import com.albionrmtempire.producer.OrderProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.albionrmtempire.util.ItemUtil.dropTierPrefix;

@Service
@Log4j2
@RequiredArgsConstructor
public class MarketDataService {

    private static final String SUCCESS = "Succeed";
    private static final String FAIL = "Failed";

    private final OrderProducer orderProducer;

    public Map<String, List<String>> publishOrders(List<OrderRequest> orders) {
        var result = orders.stream()
                .map(this::publishOrder)
                .collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toList())));

        logPublishedOrders(result);

        return result;
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
