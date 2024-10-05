package com.albionrmtempire.datatransferobject.preprocessedorders;

import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.util.ItemUtil;
import lombok.Getter;


@Getter
public class ResourceOrderRequest extends PreProcessedOrder {

    public ResourceOrderRequest(OrderRequest request) {
        super(request);
    }

    @Override
    protected String resolveSystemName(OrderRequest orderRequest) {
        return ItemUtil.getResourceSystemName(orderRequest.itemGroupTypeId());
    }
}
