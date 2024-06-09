package com.albionrmtempire.dataobject.crystalleague;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("team_core_players")
public record CorePlayer(
        @Id String id,
        String name,
        String team,
        int priority) { }
