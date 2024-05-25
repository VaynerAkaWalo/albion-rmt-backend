package com.albionrmtempire.controller;

import com.albionrmtempire.repository.ItemOrderRepository;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("api/internal")
public class InternalController {

    private final ItemOrderRepository itemOrderRepository;

    @DeleteMapping("orders")
    @ResponseStatus(HttpStatus.GONE)
    void dropOrders() {
        itemOrderRepository.deleteAll();
    }
}
