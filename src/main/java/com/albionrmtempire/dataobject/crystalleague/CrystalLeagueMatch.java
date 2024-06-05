package com.albionrmtempire.dataobject.crystalleague;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("crystal_match")
public record CrystalLeagueMatch(
        @Id String matchId,
        @Version String version,
        int winner,
        int timeOnePoints,
        int timeTwoPoints,
        int level
) {}
