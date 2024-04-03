package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;

public record Category(@Id String systemName, String displayName) {}
