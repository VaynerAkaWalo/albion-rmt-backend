package com.albionrmtempire.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ItemResponse(
        String name,
        String displayName,
        ResourceResponse resourceOne,
        ResourceResponse resourceTwo,
        CityResponse city,
        StationResponse station) {
}
