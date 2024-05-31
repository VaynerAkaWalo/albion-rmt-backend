package com.albionrmtempire.controller.v2;

import com.albionrmtempire.datatransferobject.price.resource.v2.AllResourcePriceV2;
import com.albionrmtempire.service.ResourcePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/price/resource")
@RequiredArgsConstructor
public class ResourcePriceControllerV2 {

    private final ResourcePriceService resourcePriceService;

    @GetMapping("/{resourceName}")
    AllResourcePriceV2 getPrices(
            @PathVariable("resourceName") String systemName,
            @RequestParam(value = "sessionId", required = false) String sessionId
    ) {
        return resourcePriceService.getPricesV2(systemName, sessionId);
    }
}
