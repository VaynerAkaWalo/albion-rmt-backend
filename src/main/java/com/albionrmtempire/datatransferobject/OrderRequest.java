package com.albionrmtempire.datatransferobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderRequest(
        @JsonProperty("UnitPriceSilver") long unitPrice,
        @JsonProperty("TotalPriceSilver") long totalPrice,
        @JsonProperty("Amount") long amount,
        @JsonProperty("Tier") short tier,
        @JsonProperty("EnchantmentLevel") short enchantment,
        @JsonProperty("QualityLevel") short quality,
        @JsonProperty("IsFinished") boolean isFinished,
        @JsonProperty("BuyerName") String buyer,
        @JsonProperty("ItemTypeId") String itemType,
        @JsonProperty("ItemGroupTypeId") String itemGroupType,
        @JsonProperty("Expires") String expireDate
) {}
