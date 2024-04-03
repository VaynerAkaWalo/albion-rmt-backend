package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

public record Subcategory(@Id String systemName,
                          String displayName,
                          AggregateReference<City, String> preferredCity,
                          AggregateReference<Station, String> station,
                          @MappedCollection(idColumn = "SUBCATEGORY") Set<Item> items) {}
