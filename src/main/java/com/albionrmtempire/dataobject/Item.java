package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Item(@Id String systemName,
                   String displayName,
                   AggregateReference<Resource, String> resource1,
                   AggregateReference<Resource, String> resource2,
                   Integer resource1ratio,
                   Integer resource2ratio) {
}
