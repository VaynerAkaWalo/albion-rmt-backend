package com.albionrmtempire.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemUtil {

    public static String dropTierPrefix(String string) {
        return string.replaceFirst("T\\d_", "");
    }
}
