package com.albionrmtempire.dataobject.crystalleague;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("crystal_player_match")
public record Participant(
        AggregateReference<Player, String> player,
        int team,
        int kills,
        int deaths,
        int healingDone) {
}
