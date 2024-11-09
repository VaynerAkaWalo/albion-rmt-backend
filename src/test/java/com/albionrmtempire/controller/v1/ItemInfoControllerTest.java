package com.albionrmtempire.controller.v1;

import com.albionrmtempire.datatransferobject.CategoryResponse;
import com.albionrmtempire.service.ItemInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.albionrmtempire.utils.TestUtils.TEST_CATEGORY;
import static com.albionrmtempire.utils.TestUtils.TEST_SYSTEM_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemInfoControllerTest {

    @InjectMocks
    private ItemInfoController itemInfoController;

    @Mock
    ItemInfoService itemInfoService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(itemInfoController)
                .build();
    }

    @Nested
    class categories {
        @Test
        void happyPath_returnResponse() throws Exception {
            when(itemInfoService.getAllCategories()).thenReturn(List.of(CategoryResponse.from(TEST_CATEGORY)));

            mockMvc.perform(get("/api/v1/categories"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$['categories'][0]['name']")
                            .value("CATEGORY"))
                    .andExpect(jsonPath("$['categories'][0]['subcategories'][0]['name']")
                            .value("SUBCATEGORY"))
                    .andExpect(jsonPath("$['categories'][0]['subcategories'][0]['items'][0]['name']")
                            .value(TEST_SYSTEM_NAME));
        }
    }
}
