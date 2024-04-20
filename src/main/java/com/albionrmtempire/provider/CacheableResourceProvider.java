package com.albionrmtempire.provider;

import com.albionrmtempire.dataobject.*;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CacheableResourceProvider {

    private final ItemRepository itemRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ResourceRepository resourceRepository;
    private final CityRepository cityRepository;
    private final StationRepository stationRepository;
    private final CategoryRepository categoryRepository;

    @Cacheable("items")
    public Item getItemBySystemName(String name) {
        return itemRepository.findById(name)
                .orElseThrow(() -> NotFoundException.ofItem(name));
    }

    @Cacheable("resources")
    public Resource getResourceByName(String name) {
        return resourceRepository
                .findById(name)
                .orElseThrow(() -> NotFoundException.ofResource(name));
    }

    @Cacheable("subcategories")
    public Subcategory getSubcategoryByItem(String name) {
        return subcategoryRepository
                .findByItemName(name);
    }

    @Cacheable("items")
    public Collection<Item> getAllItems() {
        return itemRepository
                .findAll();
    }

    @Cacheable("cities")
    public City getCityByName(String name) {
        return cityRepository.findById(name)
                .orElseThrow(() -> new NotFoundException("Requested city does not exists"));
    }

    @Cacheable("stations")
    public Station getStationByName(String name) {
        return stationRepository.findById(name)
                .orElseThrow(() -> new NotFoundException("Requested city does not exists"));
    }

    @Cacheable("categories")
    public Collection<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
