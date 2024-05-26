package com.albionrmtempire.datatransferobject.preprocessedorders;

import com.albionrmtempire.datatransferobject.OrderRequest;
import lombok.Getter;

@Getter
public class ItemOrderRequest extends PreProcessedOrder {

    private final short quality;

    public ItemOrderRequest(OrderRequest request) {
        super(request);
        this.quality = (short) request.quality();
    }
}
