package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;

public record Resource(@Id String systemName, String displayName) {}
