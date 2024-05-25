package com.albionrmtempire.dataobject.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResource {
    private String id;
    private String orderId;
    private String resource;
    private long ttl;
    private long amount;
    private long unitPrice;
    private short tier;
    private short enchant;
    private Instant lastUpdate;
}
