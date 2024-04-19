package com.albionrmtempire.datatransferobject;


import java.util.Date;

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
