package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;

public record Subcategory(@Id String systemName, String displayName) {}
