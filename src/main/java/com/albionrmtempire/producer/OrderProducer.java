package com.albionrmtempire.producer;

import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.datatransferobject.preprocessedorders.ItemOrderRequest;
import com.albionrmtempire.datatransferobject.preprocessedorders.PreProcessedOrder;
import com.albionrmtempire.datatransferobject.preprocessedorders.ResourceOrderRequest;
import com.albionrmtempire.exception.MalformedOrderRequestException;
import com.albionrmtempire.exception.UnsupportedOrderException;
import com.albionrmtempire.provider.CacheableResourceProvider;
import com.albionrmtempire.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
@Log4j2
public class OrderProducer {
    private static final String BLACK_MARKET = "@BLACK_MARKET";
    private static final Set<String> ALLOWED_AUCTION_TYPES = Set.of("offer", "");
    private static final Set<String> RESOURCES = Set.of(
            "PLANKS", "CLOTH", "LEATHER", "METALBAR",
            "WOOD", "ROCK", "HIDE", "FIBER");
    private static final Set<String> ARMOURS = Set.of("HEAD", "ARMOR", "SHOES");

    private final ApplicationEventPublisher publisher;
    private final CacheableResourceProvider cacheableResourceProvider;

    public String publishOrderEvent(OrderRequest orderRequest) {
        final var preProcessedOrder = preProcessOrder(orderRequest);

        publisher.publishEvent(preProcessedOrder);

        return preProcessedOrder.getSystemName();
    }

    private PreProcessedOrder preProcessOrder(OrderRequest orderRequest) {
        validateOrderRequest(orderRequest);

        if (StringUtils.equals(orderRequest.buyer(), BLACK_MARKET)) {
            validateItemOrderRequest(orderRequest);
            return new ItemOrderRequest(orderRequest);
        }

        if (!ALLOWED_AUCTION_TYPES.contains(orderRequest.auctionType())) {
            throw new UnsupportedOrderException("Unsupported action type");
        }

        if (isResource(orderRequest)) {
            return new ResourceOrderRequest(orderRequest);
        }

        throw new UnsupportedOrderException("This order type is not supported yet");
    }

    private void validateOrderRequest(OrderRequest request) {
        if (request.tier() < 1 || request.tier() > 8) {
            throw new MalformedOrderRequestException("Tier must me between 1 and 8 inclusive");
        }

        if (request.enchantment() < 0 || request.enchantment() > 4) {
            throw new MalformedOrderRequestException("Enchant must be between 0 and 4 inclusive");
        }

        if (request.amount() < 1) {
            throw new MalformedOrderRequestException("Amount must be greater than zero");
        }

        if (request.unitPrice() < 1) {
            throw new MalformedOrderRequestException("Unit price must be greater than zero");
        }

        if (request.itemGroupType().split("_").length < 2) {
            throw new MalformedOrderRequestException("Malformed item identifier");
        }

        if (StringUtils.isBlank(request.auctionType())) {
            throw new MalformedOrderRequestException("Action type cannot be empty or null");
        }
    }

    private void validateItemOrderRequest(OrderRequest request) {
        cacheableResourceProvider.getItemBySystemName(ItemUtil.getItemSystemName(request.itemGroupType()));
    }

    private boolean isResource(OrderRequest request) {
        final var itemGroupTypes = request.itemGroupType().split("_");

        final var isArmour = ARMOURS.stream()
                .anyMatch(itemGroupTypes[0]::contains);

        return !isArmour && RESOURCES.stream()
                .anyMatch(itemGroupTypes[1]::contains);
    }
}
