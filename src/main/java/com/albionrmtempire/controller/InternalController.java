package com.albionrmtempire.controller;

import com.albionrmtempire.repository.PersistedOrderRepository;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("api/internal")
public class InternalController {

    private final PersistedOrderRepository persistedOrderRepository;

    @DeleteMapping("orders")
    @ResponseStatus(HttpStatus.GONE)
    void dropOrders() {
        persistedOrderRepository.deleteAll();
    }
}
