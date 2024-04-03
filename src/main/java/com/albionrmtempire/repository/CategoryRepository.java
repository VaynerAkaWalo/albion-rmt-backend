package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, String> {

    List<Category> findAll();
}
