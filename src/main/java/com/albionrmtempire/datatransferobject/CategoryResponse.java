package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Category;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record CategoryResponse(
        String name,
        String displayName,
        List<SubcategoryResponse> subcategories) {

    public static CategoryResponse from(Category category) {
        final List<SubcategoryResponse> subcategories = category.subcategories()
                .stream()
                .map(SubcategoryResponse::from)
                .collect(Collectors.toList());

        return new CategoryResponse(category.systemName(), category.displayName(), subcategories);
    }
}
