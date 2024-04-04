package com.albionrmtempire.repository;

import com.albionrmtempire.dataobject.Category;
import com.albionrmtempire.dataobject.Subcategory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface SubcategoryRepository extends CrudRepository<Subcategory, String> {

    @Query("SELECT s.* FROM item i left join subcategory s ON i.subcategory = s.system_name where i.system_name = :name")
    Subcategory findByItemName(String name);
}
