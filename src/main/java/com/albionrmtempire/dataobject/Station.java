package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;

public record Station(@Id String systemName, String displayName) {}
