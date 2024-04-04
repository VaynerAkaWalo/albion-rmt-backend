package com.albionrmtempire.service;

import com.albionrmtempire.dataobject.Category;
import com.albionrmtempire.dataobject.Item;
import com.albionrmtempire.dataobject.Subcategory;
import com.albionrmtempire.datatransferobject.CategoryResponse;
import com.albionrmtempire.datatransferobject.ShortItemResponse;
import com.albionrmtempire.datatransferobject.SubcategoryResponse;
import com.albionrmtempire.repository.CategoryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemInfoServiceTest {

    @InjectMocks
    private ItemInfoService itemInfoService;

    @Mock
    private CategoryRepository categoryRepository;

    @Nested
    class getAllCategories {
        private final Item testItem = new Item("item_system_name", "item_display_name", null, null, null, null);
        private final Subcategory testSubcategory = new Subcategory("subcategory_system_name", "subcategory_display_name", null, null, Set.of(testItem));
        private final Category testCategory = new Category("category_system_name", "category_display_name", Set.of(testSubcategory));

        @Test
        void happyPath_returnCategoryInfo() {
            when(categoryRepository.findAll()).thenReturn(List.of(testCategory));

            final List<CategoryResponse> response = itemInfoService.getAllCategories();

            assertEquals(1, response.size());
            final CategoryResponse cr = response.getFirst();
            assertEquals(testCategory.systemName(), cr.name());
            assertEquals(testCategory.displayName(), cr.displayName());

            assertEquals(1, testCategory.subcategories().size());
            final SubcategoryResponse sr = cr.subcategories().getFirst();
            assertEquals(testSubcategory.systemName(), sr.name());
            assertEquals(testSubcategory.displayName(), sr.displayName());

            assertEquals(1, sr.items().size());
            final ShortItemResponse sir = sr.items().getFirst();
            assertEquals(testItem.systemName(), sir.name());
            assertEquals(testItem.displayName(), sir.displayName());

        }
    }
}