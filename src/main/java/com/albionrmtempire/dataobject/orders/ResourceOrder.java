package com.albionrmtempire.dataobject.orders;

import com.albionrmtempire.datatransferobject.preprocessedorders.ResourceOrderRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Builder
@Getter
@Setter
public class ResourceOrder implements PersistedOrder{

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
