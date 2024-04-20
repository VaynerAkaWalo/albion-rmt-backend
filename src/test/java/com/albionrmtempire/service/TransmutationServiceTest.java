package com.albionrmtempire.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TransmutationServiceTest {

    private TransmutationService transmutationService;

    @BeforeEach
    void setUp() {
        transmutationService = new TransmutationService();
    }


    @Nested
    class transmutationCost {

        @ParameterizedTest
        @MethodSource
        void flat_returnsCorrectCosts(int expectedCost, int tier) {
            assertEquals(expectedCost, transmutationService.transmutationCost(tier, 0));
        }

        private static Stream<Arguments> flat_returnsCorrectCosts() {
            return Stream.of(
                    Arguments.of(0, 4),
                    Arguments.of(2343, 5),
                    Arguments.of(7343, 6),
                    Arguments.of(19843, 7),
                    Arguments.of(44843, 8)
            );
        }
    }


}