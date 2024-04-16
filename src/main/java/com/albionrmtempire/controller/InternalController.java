package com.albionrmtempire.controller;

import com.albionrmtempire.dataobject.PersistedOrder;
import com.albionrmtempire.repository.PersistedOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
