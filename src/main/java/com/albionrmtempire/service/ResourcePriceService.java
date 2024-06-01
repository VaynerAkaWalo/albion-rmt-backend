package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.orders.ResourceOrder;
import com.albionrmtempire.datatransferobject.price.resource.AllResourcePrice;
import com.albionrmtempire.datatransferobject.price.resource.ResourcePrice;
import com.albionrmtempire.datatransferobject.price.resource.TierResourcePrice;
import com.albionrmtempire.datatransferobject.price.resource.v2.AllResourcePriceV2;
import com.albionrmtempire.datatransferobject.price.resource.v2.ResourcePriceV2;
import com.albionrmtempire.repository.ResourceOrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ResourcePriceService {

    private final ResourceOrderRepository orderRepository;

    public AllResourcePriceV2 getPricesV2(@NonNull String systemName, String sessionId) {
        final var orders = getOrders(systemName, sessionId);

        var groupedByTiers = orders.stream()
                .collect(Collectors.groupingBy(ResourceOrder::getTier));

        return new AllResourcePriceV2(
                systemName,
                groupedByTiers.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> processOrdersAtTier(entry.getValue())))
        );
    }

    private Map<Short, ResourcePriceV2> processOrdersAtTier(List<ResourceOrder> orders) {
        final var groupByEnchant = orders.stream()
                .collect(Collectors.groupingBy(ResourceOrder::getEnchant));

        return groupByEnchant.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> processOrdersAtEnchant(entry.getValue())));
    }

    private ResourcePriceV2 processOrdersAtEnchant(List<ResourceOrder> orders) {
        final PriceAccumulator accumulatedPrices = orders.stream()
                .sorted(Comparator.comparing(ResourceOrder::getUnitPrice))
                .collect(PriceAccumulator::new, PriceAccumulator::accept, PriceAccumulator::combine);

        return new ResourcePriceV2(
                accumulatedPrices.lowestPrice,
                accumulatedPrices.firstTenPrice,
                accumulatedPrices.firstHundredPrice,
                accumulatedPrices.firstThousandPrice,
                accumulatedPrices.firstTenThousandsPrice,
                accumulatedPrices.totalAmount
        );
    }

    public AllResourcePrice getPrices(@NonNull String systemName, String sessionId) {
        final var orders = getOrders(systemName, sessionId);

        var groupedByTiers = orders.stream()
                .collect(Collectors.groupingBy(ResourceOrder::getTier));

        return new AllResourcePrice(
                priceForTier(groupedByTiers.get((short)2)),
                priceForTier(groupedByTiers.get((short)3)),
                priceForTier(groupedByTiers.get((short)4)),
                priceForTier(groupedByTiers.get((short)5)),
                priceForTier(groupedByTiers.get((short)6)),
                priceForTier(groupedByTiers.get((short)7)),
                priceForTier(groupedByTiers.get((short)8))
        );
    }

    private List<ResourceOrder> getOrders(String systemName, String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            return orderRepository.findAllBySystemNameAndSessionId(systemName, sessionId);
        }
        return orderRepository.findAllBySystemName(systemName);
    }

    private TierResourcePrice priceForTier(List<ResourceOrder> orders) {
        if (orders == null) {
            return null;
        }

        var groupedByEnchant = orders.stream()
                .collect(Collectors.groupingBy(ResourceOrder::getEnchant));

        return new TierResourcePrice(
                priceForEnchant(groupedByEnchant.get((short)0)),
                priceForEnchant(groupedByEnchant.get((short)1)),
                priceForEnchant(groupedByEnchant.get((short)2)),
                priceForEnchant(groupedByEnchant.get((short)3)),
                priceForEnchant(groupedByEnchant.get((short)4))
        );
    }

    private ResourcePrice priceForEnchant(List<ResourceOrder> orders) {
        if (orders == null) {
            return null;
        }

        final PriceAccumulator accumulatedPrices = orders.stream()
                .sorted(Comparator.comparing(ResourceOrder::getUnitPrice))
                .collect(PriceAccumulator::new, PriceAccumulator::accept, PriceAccumulator::combine);

        return new ResourcePrice(
                accumulatedPrices.lowestPrice,
                accumulatedPrices.firstTenPrice,
                accumulatedPrices.firstHundredPrice,
                accumulatedPrices.firstThousandPrice,
                accumulatedPrices.firstTenThousandsPrice,
                accumulatedPrices.totalAmount
        );
    }

    private static class PriceAccumulator {

        private long lowestPrice;
        private long firstTenPrice;
        private long firstHundredPrice;
        private long firstThousandPrice;
        private long firstTenThousandsPrice;
        private long totalPrice;
        private long totalAmount;

        public PriceAccumulator() {
            this.firstTenPrice = 0;
            this.firstHundredPrice = 0;
            this.firstThousandPrice = 0;
            this.firstTenThousandsPrice = 0;
            this.totalAmount = 0;
        }

        public void accept(ResourceOrder currentOrder) {
            final long newTotalAmount = totalAmount + currentOrder.getAmount();

            if (lowestPrice == 0) {
                lowestPrice = currentOrder.getUnitPrice();
            }

            if (firstTenPrice == 0 && newTotalAmount >= 10) {
                firstTenPrice = (totalPrice + (10 - totalAmount) * currentOrder.getUnitPrice()) / 10;
            }

            if (firstHundredPrice == 0 && newTotalAmount >= 100) {
                firstHundredPrice = (totalPrice + (100 - totalAmount) * currentOrder.getUnitPrice()) / 100;
            }

            if (firstThousandPrice == 0 && newTotalAmount >= 1_000) {
                firstThousandPrice = (totalPrice + (1_000 - totalAmount) * currentOrder.getUnitPrice()) / 1_000;
            }

            if (firstTenThousandsPrice == 0 && newTotalAmount >= 10_000) {
                firstTenThousandsPrice = (totalPrice + (10_000 - totalAmount) * currentOrder.getUnitPrice()) / 10_000;
            }

            totalAmount = newTotalAmount;
            totalPrice += currentOrder.getAmount() * currentOrder.getUnitPrice();
        }

        public void combine(PriceAccumulator priceAccumulator) {
            throw new UnsupportedOperationException("Cannot combine two accumulators");
        }
    }
}
