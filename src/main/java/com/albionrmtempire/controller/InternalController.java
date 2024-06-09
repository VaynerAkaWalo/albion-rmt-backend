package com.albionrmtempire.controller;

import com.albionrmtempire.provider.AlbionApiProvider;
import com.albionrmtempire.repository.ItemOrderRepository;
import com.albionrmtempire.service.crystalleague.CrystalMatchService;
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
    private final AlbionApiProvider albionApiProvider;
    private final CrystalMatchService crystalMatchService;

    @DeleteMapping("orders")
    @ResponseStatus(HttpStatus.GONE)
    void dropOrders() {
        itemOrderRepository.deleteAll();
    }

    @GetMapping("fetchMatches")
    void fetchAllCrystalMatches() {
        final var matches = albionApiProvider.getAllMatches();

        matches.forEach(crystalMatchService::persistMatchResults);
    }
}
