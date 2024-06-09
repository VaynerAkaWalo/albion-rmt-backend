package com.albionrmtempire.dataobject.crystalleague;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.List;

@Table("crystal_match")
public record Match(
        @Id String matchId,
        @Version Integer version,
        int winner,
        int teamOnePoints,
        int teamTwoPoints,
        int level,
        Instant startTime,
        List<Participant> players
) {}
