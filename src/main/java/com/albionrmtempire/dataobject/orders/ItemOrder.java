package com.albionrmtempire.dataobject.orders;

import com.albionrmtempire.datatransferobject.OrderResponse;
import com.albionrmtempire.datatransferobject.preprocessedorders.ItemOrderRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class ItemOrder extends PersistedOrder {

    private short quality;

    @Builder
    public ItemOrder(long id, long ttl, long orderId, long unitPrice, long amount, short tier, short enchant, String buyer, String systemName, String sessionId, Instant lastUpdate, short quality) {
        super(id, ttl, orderId, unitPrice, amount, tier, enchant, buyer, systemName, sessionId, lastUpdate);
        this.quality = quality;
    }

    public static ItemOrder fromDto(ItemOrderRequest orderRequest) {
        return ItemOrder.builder()
                .orderId(orderRequest.getOrderId())
                .ttl(DEFAULT_TTL)
                .unitPrice(orderRequest.getUnitPrice())
                .amount(orderRequest.getAmount())
                .tier(orderRequest.getTier())
                .enchant(orderRequest.getEnchant())
                .quality(orderRequest.getQuality())
                .buyer(orderRequest.getBuyer())
                .systemName(orderRequest.getSystemName())
                .sessionId(orderRequest.getSessionId())
                .lastUpdate(Instant.now())
                .build();
    }

    public OrderResponse toDto(String displayName) {
        return OrderResponse.builder()
                .orderId(this.getOrderId())
                .name(this.getSystemName())
                .displayName(StringUtils.isNotBlank(displayName) ? displayName : this.getSystemName())
                .amount(this.getAmount())
                .tier(this.getTier())
                .enchant(this.getEnchant())
                .quality(quality)
                .unitPrice(this.getUnitPrice())
                .lastUpdate(Date.from(this.getLastUpdate()))
                .build();
    }
}
