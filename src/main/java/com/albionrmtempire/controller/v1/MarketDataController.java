package com.albionrmtempire.controller.v1;


import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.datatransferobject.OrderResponse;
import com.albionrmtempire.provider.CachedMarketDataProvider;
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
@CrossOrigin
public class MarketDataController {

    private final MarketDataService marketDataService;
    private final CachedMarketDataProvider cachedMarketDataProvider;

    @PostMapping
    ResponseEntity<Map<String, List<String>>> postOrder(@RequestBody List<OrderRequest> orders) {
        var response = marketDataService.publishOrdersRequestEvents(orders);
        if (!response.containsKey("Succeed")) {
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    Collection<OrderResponse> getAllOrders() {
        return cachedMarketDataProvider.getAllNotExpiredItemOrders();
    }

    @DeleteMapping("/{itemId}")
    void deleteItem(@PathVariable("itemId") long id) {
        marketDataService.pseudoDeleteOrder(id);
    }
}
