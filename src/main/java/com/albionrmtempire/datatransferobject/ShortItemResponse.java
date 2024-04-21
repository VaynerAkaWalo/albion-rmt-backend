package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Item;
import org.apache.commons.lang3.StringUtils;

public record ShortItemResponse(String name, String displayName) {

    public static ShortItemResponse from(Item item) {
        return new ShortItemResponse(item.systemName(), StringUtils.isEmpty(item.displayName()) ? item.systemName() : item.displayName());
    }
}
