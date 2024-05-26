package com.albionrmtempire.datatransferobject.price.resource;

public record TierResourcePrice(
        ResourcePrice level0,
        ResourcePrice level1,
        ResourcePrice level2,
        ResourcePrice level3,
        ResourcePrice level4
) {}
