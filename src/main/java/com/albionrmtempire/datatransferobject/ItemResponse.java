package com.albionrmtempire.datatransferobject;

import lombok.Builder;

@Builder
public record ItemResponse(
        String name,
        String displayName,
        ResourceResponse resourceOne,
        ResourceResponse resourceTwo,
        CityResponse city,
        StationResponse station) {
}
