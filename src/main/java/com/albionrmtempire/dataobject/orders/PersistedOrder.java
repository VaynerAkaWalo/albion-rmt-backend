package com.albionrmtempire.dataobject.orders;

import com.albionrmtempire.datatransferobject.preprocessedorders.PreProcessedOrder;

public interface PersistedOrder {

    long DEFAULT_TTL = 20;

    default PersistedOrder updateOrder(PreProcessedOrder order) {
        return this;
    }
}
