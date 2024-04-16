package com.albionrmtempire.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistedOrder {

    @Id
    private String id;

    private AggregateReference<Item, String> item;

    private long amount;
    private long unitPrice;

    private short tier;
    private short enchant;
    private short quality;

    private Date expire;
}