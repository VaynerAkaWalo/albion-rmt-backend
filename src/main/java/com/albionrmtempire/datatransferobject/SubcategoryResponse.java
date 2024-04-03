package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Subcategory;

import java.util.Set;
import java.util.stream.Collectors;

public record SubcategoryResponse(
        String name,
        String displayName,
        Set<ShortItemResponse> items) {

    public static SubcategoryResponse from(Subcategory subcategory) {
        final Set<ShortItemResponse> items = subcategory.items()
                .stream()
                .map(ShortItemResponse::from)
                .collect(Collectors.toSet());

        return new SubcategoryResponse(subcategory.systemName(), subcategory.displayName(), items);
    }
}
