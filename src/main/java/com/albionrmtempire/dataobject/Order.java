package com.albionrmtempire.dataobject;

import com.albionrmtempire.datatransferobject.OrderRequest;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record Order(
        long unitPrice,
        long amount,
        short tier,
        short enchant,
        short quality,
        boolean isFinished,
        String buyer,
        Item item,
        ZonedDateTime acknowledgedDate,
        ZonedDateTime expire
) {}
