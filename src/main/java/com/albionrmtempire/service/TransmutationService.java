package com.albionrmtempire.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class TransmutationService {
    private final int TIER_OFFSET = 4;
    private final int[] MATERIALS_AT_TIER = new int[] {2, 3, 4, 5, 5};
    private final long[][] ENCHANT_COST = new long[][] {
            {0, 1_500, 3_000, 6_000, 24_000},
            {781, 2_000, 4_000, 8_000, 32_000},
            {1_250, 3_000, 6_000, 19_800, 79_200},
            {2_500, 4_800, 15_120, 49_896, 199_584},
            {5_000, 14_400, 45_360, 149_688, 748_832},
    };

    public long transmutationCost(int tier, int enchant) {
        Supplier<Long> totalCostAtTier
                = () -> MATERIALS_AT_TIER[tier - TIER_OFFSET] * costAtTier(tier, enchant);

        if (tier == 4) {
            return totalCostAtTier.get();
        }
        return transmutationCost(tier - 1, enchant) + totalCostAtTier.get();
    }

    private long costAtTier(int tier, int enchant) {
        Supplier<Long> currentCost
                = () -> ENCHANT_COST[tier - TIER_OFFSET][enchant];

        if (enchant == 0) {
            return currentCost.get();
        }
        return costAtTier(tier, enchant - 1) + currentCost.get();
    }
}
