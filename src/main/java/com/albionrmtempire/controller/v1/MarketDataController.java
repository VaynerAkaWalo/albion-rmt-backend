package com.albionrmtempire.controller.v1;


import com.albionrmtempire.dataobject.PersistedOrder;
import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.producer.OrderProducer;
import com.albionrmtempire.repository.PersistedOrderRepository;
import com.albionrmtempire.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/marketdata")
@RequiredArgsConstructor
public class MarketDataController {

    private final MarketDataService marketDataService;
    private final PersistedOrderRepository orderRepository;

    @PostMapping
    ResponseEntity<Map<String, List<String>>> postOrder(@RequestBody List<OrderRequest> orders) {
        var response = marketDataService.publishOrders(orders);
        if (!response.containsKey("Succeed")) {
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        };

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    Collection<PersistedOrder> getAllOrders() {
        return orderRepository.findAllByTtlGreaterThan(0);
    }
}
