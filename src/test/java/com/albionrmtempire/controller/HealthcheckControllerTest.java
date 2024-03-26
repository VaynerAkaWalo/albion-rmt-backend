package com.albionrmtempire.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HealthcheckControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new HealthcheckController())
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/health", "/healthcheck"})
    void happyPath(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status").value("OK"));
    }
}