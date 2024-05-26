package com.albionrmtempire.dataobject.orders;


import com.albionrmtempire.datatransferobject.preprocessedorders.PreProcessedOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersistedOrder {

    public static final long DEFAULT_TTL = 24;

    @Id
    private long id;
    private long ttl;

    private long orderId;
    private long unitPrice;
    private long amount;
    private short tier;
    private short enchant;
    private String buyer;
    private String systemName;

    private String sessionId;
    private Instant lastUpdate;

    public void updateOrder(PreProcessedOrder preProcessedOrder) {
        unitPrice = preProcessedOrder.getUnitPrice();
        amount = preProcessedOrder.getAmount();
        lastUpdate = Instant.now();
    }
}
