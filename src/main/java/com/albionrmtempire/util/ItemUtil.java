package com.albionrmtempire.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemUtil {

    public static String dropTierPrefix(String string) {
        return string.split("_")[1];
    }

    public static String randomSessionId() {
        return UUID.randomUUID().toString();
    }
}
