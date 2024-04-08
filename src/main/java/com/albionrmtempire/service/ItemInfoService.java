package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.*;
import com.albionrmtempire.datatransferobject.*;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ItemRepository itemRepository;
    private final CityRepository cityRepository;
    private final ResourceRepository resourceRepository;
    private final StationRepository stationRepository;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    public ItemResponse getByName(@NonNull String name) {
        final Optional<Item> optionalItem = itemRepository.findById(name);
        if (optionalItem.isEmpty()) {
            throw NotFoundException.ofItem(name);
        }
        final Item item = optionalItem.get();
        final Subcategory subcategory = subcategoryRepository.findByItemName(item.systemName());
        return ItemResponse.builder()
                .name(item.systemName())
                .displayName(item.displayName())
                .city(cityResponse(subcategory.preferredCity() == null ? null : subcategory.preferredCity().getId()))
                .station(stationResponse(subcategory.station().getId()))
                .resourceOne(resourceResponse(item.resource1().getId(), item.resource1ratio()))
                .resourceTwo(resourceResponse(item.resource2().getId(), item.resource2ratio()))
                .build();
    }

    private StationResponse stationResponse(String name) {
        if (name == null) return null;
        final Optional<Station> optional = stationRepository.findById(name);

        return optional.map(station -> new StationResponse(station.displayName()))
                .orElse(null);
    }

    private CityResponse cityResponse(String name) {
        if (name == null) return null;
        final Optional<City> optional = cityRepository.findById(name);
        return optional
                .map(city -> new CityResponse(city.displayName(), 35.2))
                .orElse(null);
    }

    private ResourceResponse resourceResponse(String name, Integer amount) {
        if (name == null) return null;
        final Optional<Resource> optional = resourceRepository.findById(name);

        return optional.map(resource -> new ResourceResponse(name, resource.displayName(), amount))
                .orElse(null);
    }
}
