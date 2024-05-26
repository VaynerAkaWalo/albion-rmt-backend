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

@Builder
@Getter
@Setter
public class ItemOrder implements PersistedOrder {

    @Id
    private long id;
    private long ttl;

    private long orderId;
    private long unitPrice;
    private long amount;
    private short tier;
    private short enchant;
    private short quality;
    private String buyer;
    private String systemName;

    private String sessionId;
    private Instant lastUpdate;

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
                .orderId(orderId)
                .name(systemName)
                .displayName(StringUtils.isNotBlank(displayName) ? displayName : systemName)
                .amount(amount)
                .tier(tier)
                .enchant(enchant)
                .quality(quality)
                .unitPrice(unitPrice)
                .lastUpdate(Date.from(lastUpdate))
                .build();
    }
}
