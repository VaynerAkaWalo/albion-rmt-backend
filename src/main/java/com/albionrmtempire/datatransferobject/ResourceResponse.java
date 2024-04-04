package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Resource;

public record ResourceResponse(String name, String displayName, Integer amount) {

    public static ResourceResponse from(Resource resource, int amount) {
        return new ResourceResponse(resource.systemName(), resource.displayName(), amount);
    }
}
