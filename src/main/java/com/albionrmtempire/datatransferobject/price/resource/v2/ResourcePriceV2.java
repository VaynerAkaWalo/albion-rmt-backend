package com.albionrmtempire.datatransferobject.price.resource.v2;

public record ResourcePriceV2(
        long lowestPrice,
        long tenAvg,
        long hundredAvg,
        long thousandAvg,
        long tenThousandsAvg,
        long totalCount
) {}
