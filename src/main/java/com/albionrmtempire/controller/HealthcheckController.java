package com.albionrmtempire.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HealthcheckController {

    @GetMapping({"/health", "/healthcheck"})
    Map<String, String> healthcheck() {
        return Map.of("status", "OK");
    }
}
