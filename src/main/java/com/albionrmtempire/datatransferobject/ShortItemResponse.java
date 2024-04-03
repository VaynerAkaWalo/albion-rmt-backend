package com.albionrmtempire.datatransferobject;

import com.albionrmtempire.dataobject.Item;

public record ShortItemResponse(String name, String displayName) {

    public static ShortItemResponse from(Item item) {
        return new ShortItemResponse(item.systemName(), item.displayName());
    }
}
