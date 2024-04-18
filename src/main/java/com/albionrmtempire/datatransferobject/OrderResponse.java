package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.PersistedOrder;

import java.util.Date;

public record OrderResponse(
        String name,
        String displayName,
        long amount,
        long unitPrice,
        short tier,
        short enchant,
        short quality,
        Date lastUpdate) {
}
