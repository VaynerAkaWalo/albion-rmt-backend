package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Subcategory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record SubcategoryResponse(
        String name,
        String displayName,
        List<ShortItemResponse> items) {

    public static SubcategoryResponse from(Subcategory subcategory) {
        final List<ShortItemResponse> items = subcategory.items()
                .stream()
                .map(ShortItemResponse::from)
                .collect(Collectors.toList());

        return new SubcategoryResponse(subcategory.systemName(), subcategory.displayName(), items);
    }
}
