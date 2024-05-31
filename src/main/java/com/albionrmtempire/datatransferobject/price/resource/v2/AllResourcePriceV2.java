package com.albionrmtempire.datatransferobject.price.resource.v2;

import java.util.Map;

public record AllResourcePriceV2(
        String name,
        Map<Short, Map<Short, ResourcePriceV2>> prices
) {}