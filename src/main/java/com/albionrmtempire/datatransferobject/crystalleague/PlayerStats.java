package com.albionrmtempire.datatransferobject.crystalleague;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlayerStats(
        @JsonProperty("Name") String name,
        @JsonProperty("Kills") int kills,
        @JsonProperty("Deaths") int deaths,
        @JsonProperty("Healing") int healingDone) {}
