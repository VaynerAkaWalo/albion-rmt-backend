package com.albionrmtempire.dataobject.orders;

import com.albionrmtempire.datatransferobject.preprocessedorders.ResourceOrderRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Getter
@Setter
public class ResourceOrder extends PersistedOrder{

    @Builder
    public ResourceOrder(long id, long ttl, long orderId, long unitPrice, long amount, short tier, short enchant, String buyer, String systemName, String sessionId, Instant lastUpdate) {
        super(id, ttl, orderId, unitPrice, amount, tier, enchant, buyer, systemName, sessionId, lastUpdate);
    }

    public static ResourceOrder fromDto(ResourceOrderRequest orderRequest) {
        return ResourceOrder.builder()
                .orderId(orderRequest.getOrderId())
                .ttl(DEFAULT_TTL)
                .unitPrice(orderRequest.getUnitPrice())
                .amount(orderRequest.getAmount())
                .tier(orderRequest.getTier())
                .enchant(orderRequest.getEnchant())
                .buyer(orderRequest.getBuyer())
                .systemName(orderRequest.getSystemName())
                .sessionId(orderRequest.getSessionId())
                .lastUpdate(Instant.now())
                .build();
    }
}
