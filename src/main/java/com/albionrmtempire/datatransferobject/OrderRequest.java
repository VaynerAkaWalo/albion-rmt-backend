package com.albionrmtempire.datatransferobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = {JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES})
public record OrderRequest(
        long id,
        long unitPriceSilver,
        long amount,
        long tier,
        long enchantmentLevel,
        String auctionType,
        long qualityLevel,
        boolean isFinished,
        String buyerName,
        String itemTypeId,
        String itemGroupTypeId,
        String expires,
        String sessionId
) {}
