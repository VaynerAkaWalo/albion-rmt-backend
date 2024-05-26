package com.albionrmtempire.datatransferobject;


import lombok.Builder;

import java.util.Date;

@Builder
public record OrderResponse(
        String name,
        String displayName,
        long orderId,
        long amount,
        long unitPrice,
        short tier,
        short enchant,
        short quality,
        Date lastUpdate) {
}
