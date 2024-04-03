package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

public record Category(@Id String systemName,
                       String displayName,
                       @MappedCollection(idColumn = "CATEGORY") Set<Subcategory> subcategories) {}
