package com.albionrmtempire.controller.v1;

import com.albionrmtempire.datatransferobject.CategoriesResponse;
import com.albionrmtempire.datatransferobject.CategoryResponse;
import com.albionrmtempire.repository.ItemRepository;
import com.albionrmtempire.service.ItemInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
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
    CategoriesResponse categories() {
        return new CategoriesResponse(itemInfoService.getAllCategories());
    }
}
