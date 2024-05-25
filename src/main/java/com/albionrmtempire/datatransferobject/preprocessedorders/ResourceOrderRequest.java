package com.albionrmtempire.datatransferobject.preprocessedorders;

import com.albionrmtempire.datatransferobject.OrderRequest;
import lombok.Getter;


@Getter
public class ResourceOrderRequest extends PreProcessedOrder {

    public ResourceOrderRequest(OrderRequest request) {
        super(request);
    }
}
