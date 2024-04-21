package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.*;
import com.albionrmtempire.datatransferobject.*;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.provider.CacheableResourceProvider;
import com.albionrmtempire.repository.*;
import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

    private final CacheableResourceProvider cacheableResourceProvider;

    public List<CategoryResponse> getAllCategories() {
        return cacheableResourceProvider.getAllCategories()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    public ItemResponse getByName(@NonNull String name) {
        return itemToDto(cacheableResourceProvider.getItemBySystemName(name));
    }

    private StationResponse stationResponse(String name) {
        if (name == null) return null;
        final Station station = cacheableResourceProvider.getStationByName(name);

        return new StationResponse(station.displayName());
    }

    private CityResponse cityResponse(String name) {
        if (name == null) return null;
        final City city = cacheableResourceProvider.getCityByName(name);
        return new CityResponse(city.displayName(), 35.2);
    }

    private ResourceResponse resourceResponse(String name, Integer amount) {
        if (name == null) return null;
        final Resource resource = cacheableResourceProvider.getResourceByName(name);

        return new ResourceResponse(name, resource.displayName(), amount);
    }

    public ItemResponse itemToDto(Item item) {
        final Subcategory subcategory = cacheableResourceProvider.getSubcategoryByItem(item.systemName());
        return ItemResponse.builder()
                .name(item.systemName())
                .displayName(StringUtils.isEmpty(item.displayName()) ? item.systemName() : item.displayName())
                .city(cityResponse(subcategory.preferredCity() == null ? null : subcategory.preferredCity().getId()))
                .station(stationResponse(subcategory.station().getId()))
                .resourceOne(resourceResponse(item.resource1().getId(), item.resource1ratio()))
                .resourceTwo(item.resource2() != null ? resourceResponse(item.resource2().getId(), item.resource2ratio()) : null)
                .build();
    }
}
