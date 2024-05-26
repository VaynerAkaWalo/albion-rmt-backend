package com.albionrmtempire.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemUtil {

    public static String getItemSystemName(String itemGroupTypeId) {
        return itemGroupTypeId.replaceFirst("T\\d_", "");
    }

    public static String getResourceSystemName(String itemGroupTypeId) {
        return itemGroupTypeId.split("_")[1];
    }

    public static String randomSessionId() {
        return UUID.randomUUID().toString();
    }
}
