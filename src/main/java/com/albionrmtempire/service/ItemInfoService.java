package com.albionrmtempire.service;

import com.albionrmtempire.datatransferobject.CategoryResponse;
import com.albionrmtempire.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

    private final CategoryRepository categoryRepository;

    public Collection<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toSet());
    }
}
