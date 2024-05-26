package com.albionrmtempire.datatransferobject.price.resource;

public record ResourcePrice(
        Long lowestPrice,
        Long tenAvgPrice,
        Long hundredAvgPrice,
        Long thousandAvgPrice,
        Long tenThousandsAvgPrice,
        Long totalAmountOnMarket
) {}
