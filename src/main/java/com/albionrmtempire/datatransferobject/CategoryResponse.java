package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Category;

import java.util.Set;
import java.util.stream.Collectors;

public record CategoryResponse(
        String name,
        String displayName,
        Set<SubcategoryResponse> subcategories) {

    public static CategoryResponse from(Category category) {
        final Set<SubcategoryResponse> subcategories = category.subcategories()
                .stream()
                .map(SubcategoryResponse::from)
                .collect(Collectors.toSet());

        return new CategoryResponse(category.systemName(), category.displayName(), subcategories);
    }
}
