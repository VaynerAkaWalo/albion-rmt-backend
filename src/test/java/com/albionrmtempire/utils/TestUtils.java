package com.albionrmtempire.utils;


import com.albionrmtempire.dataobject.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    public static String TEST_SYSTEM_NAME = "systemName";
    public static String TEST_DISPLAY_NAME = "displayName";


    public static final int TEST_RESOURCE_ONE_AMOUNT = 8;
    public static final int TEST_RESOURCE_TWO_AMOUNT = 12;
    public static final Resource TEST_RESOURCE_ONE = new Resource("RESOURCE1", "resource1");
    public static final Resource TEST_RESOURCE_TWO = new Resource("RESOURCE2", "resource2");

    public static final Item TEST_ITEM = new Item(
            TEST_SYSTEM_NAME,
            TEST_DISPLAY_NAME,
            AggregateReference.to(TEST_RESOURCE_ONE.systemName()),
            AggregateReference.to(TEST_RESOURCE_TWO.systemName()),
            TEST_RESOURCE_ONE_AMOUNT,
            TEST_RESOURCE_TWO_AMOUNT);

    public static final City TEST_CITY = new City("CITY", "city");
    public static final Station TEST_STATION = new Station("CITY", "city");

    public static final Subcategory TEST_SUBCATEGORY = new Subcategory(
            "SUBCATEGORY",
            "subcategory",
            AggregateReference.to(TEST_CITY.systemName()),
            AggregateReference.to(TEST_STATION.systemName()),
            Set.of(TEST_ITEM));

    public static final Category TEST_CATEGORY = new Category(
            "CATEGORY",
            "category",
            Set.of(TEST_SUBCATEGORY));
}
