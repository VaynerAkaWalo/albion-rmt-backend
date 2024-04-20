package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Item;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ItemResponse(
        String name,
        String displayName,
        ResourceResponse resourceOne,
        ResourceResponse resourceTwo,
        CityResponse city,
        StationResponse station) { }
