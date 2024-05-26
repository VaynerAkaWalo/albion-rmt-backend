package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.orders.ResourceOrder;
import com.albionrmtempire.datatransferobject.price.resource.AllResourcePrice;
import com.albionrmtempire.datatransferobject.price.resource.ResourcePrice;
import com.albionrmtempire.datatransferobject.price.resource.TierResourcePrice;
import com.albionrmtempire.repository.ResourceOrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ResourcePriceService {

    private final ResourceOrderRepository orderRepository;

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
            totalAmount += currentOrder.getAmount();
            totalPrice += currentOrder.getAmount() * currentOrder.getUnitPrice();

            if (lowestPrice == 0) {
                lowestPrice = currentOrder.getUnitPrice();
            }
            if (totalAmount >= 10 && firstTenPrice == 0) {
                firstTenPrice = totalPrice / totalAmount;
            }
            if (totalAmount >= 100 && firstHundredPrice == 0) {
                firstHundredPrice = totalPrice / totalAmount;
            }
            if (totalAmount >= 1_000 && firstThousandPrice == 0) {
                firstThousandPrice = totalPrice / totalAmount;
            }
            if (totalAmount >= 10_000 && firstTenThousandsPrice == 0) {
                firstTenThousandsPrice = totalPrice / totalAmount;
            }
        }

        public void combine(PriceAccumulator priceAccumulator) {
            throw new UnsupportedOperationException("Cannot combine two accumulators");
        }
    }
}
