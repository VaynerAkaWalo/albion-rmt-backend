package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Subcategory(@Id String systemName,
                          String displayName,
                          AggregateReference<City, String> preferredCity,
                          AggregateReference<Station, String> station) {}
