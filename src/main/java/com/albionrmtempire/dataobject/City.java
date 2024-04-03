package com.albionrmtempire.dataobject;

import org.springframework.data.annotation.Id;

public record City(@Id String systemName, String displayName) {}
