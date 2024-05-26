package com.albionrmtempire.controller.v1;

import com.albionrmtempire.datatransferobject.price.resource.AllResourcePrice;
import com.albionrmtempire.service.ResourcePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/price/resource")
public class ResourcePriceController {

    private final ResourcePriceService resourcePriceService;

    @GetMapping("/{resourceName}")
    public AllResourcePrice getResourcePrice(
            @PathVariable("resourceName") String systemName,
            @RequestParam(value = "sessionId", required = false) String sessionId
    ) {
        return resourcePriceService.getPrices(systemName, sessionId);
    }
}
