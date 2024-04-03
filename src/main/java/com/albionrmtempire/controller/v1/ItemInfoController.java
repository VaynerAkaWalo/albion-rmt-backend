package com.albionrmtempire.controller.v1;

import com.albionrmtempire.datatransferobject.CategoryResponse;
import com.albionrmtempire.service.ItemInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemInfoController {

    private final ItemInfoService itemInfoService;

    @GetMapping("/categories")
    Collection<CategoryResponse> categories() {
        return itemInfoService.getAllCategories();
    }
}
