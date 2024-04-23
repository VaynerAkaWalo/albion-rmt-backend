package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Subcategory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubcategoryResponse(
        String name,
        String displayName,
        String recommendedCity,
        List<ShortItemResponse> items) {

    public static SubcategoryResponse from(Subcategory subcategory) {
        final List<ShortItemResponse> items = subcategory.items()
                .stream()
                .map(ShortItemResponse::from)
                .collect(Collectors.toList());

        return new SubcategoryResponse(
                subcategory.systemName(),
                subcategory.displayName(),
                subcategory.preferredCity() != null ? subcategory.preferredCity().getId() : null,
                items);
    }
}
