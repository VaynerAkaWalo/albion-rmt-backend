package com.albionrmtempire.controller.v1;

import com.albionrmtempire.datatransferobject.CategoriesResponse;
import com.albionrmtempire.datatransferobject.ItemResponse;
import com.albionrmtempire.service.ItemInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemInfoController {

    private final ItemInfoService itemInfoService;

    @GetMapping("/categories")
    CategoriesResponse categories() {
        return new CategoriesResponse(itemInfoService.getAllCategories());
    }

    @GetMapping("/item/{item}")
    ItemResponse getItemByName(@PathVariable("item") String name) {
        return itemInfoService.getByName(name);
    }
}
