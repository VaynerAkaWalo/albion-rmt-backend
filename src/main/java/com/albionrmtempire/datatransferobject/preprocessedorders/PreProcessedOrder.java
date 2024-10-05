package com.albionrmtempire.datatransferobject.preprocessedorders;

import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.util.ItemUtil;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
public abstract class PreProcessedOrder {

    private final long orderId;
    private final long unitPrice;
    private final long amount;
    private final short tier;
    private final short enchant;
    private final String buyer;
    private final String systemName;
    private final String sessionId;
    private final Instant acknowledgedDate;

    protected PreProcessedOrder(OrderRequest request) {
        this.orderId = request.id();
        this.unitPrice = request.unitPriceSilver() / 10000;
        this.amount = request.amount();
        this.tier = (short) request.tier();
        this.enchant = (short) request.enchantmentLevel();
        this.buyer = request.buyerName();
        this.systemName = resolveSystemName(request);
        this.sessionId = Objects.nonNull(request.sessionId()) ? request.sessionId() : ItemUtil.randomSessionId();
        this.acknowledgedDate = Instant.now();
    }

    protected String resolveSystemName(OrderRequest orderRequest) {
        return ItemUtil.getItemSystemName(orderRequest.itemGroupTypeId());
    }
}
