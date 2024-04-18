package com.albionrmtempire.dataobject;

import java.util.Date;

public record Order(
        long id,
        long unitPrice,
        long amount,
        short tier,
        short enchant,
        short quality,
        boolean isFinished,
        String buyer,
        Item item,
        Date acknowledgedDate,
        Date expire
) {}
