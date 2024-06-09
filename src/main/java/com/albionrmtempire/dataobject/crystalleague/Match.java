package com.albionrmtempire.dataobject.crystalleague;

import com.albionrmtempire.exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
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
        AggregateReference<Player, String> teamOneLeader,
        AggregateReference<Player, String> teamTwoLeader,
        List<Participant> players
) {

    public List<Participant> getTeammatesOfPlayer(String playerId) {
        final var team = getPlayersTeam(playerId);

        if (team == 0) {
            return List.of();
        }

        return getPlayersFromTeam(team).stream()
                .filter(participant -> !StringUtils.equals(participant.player().getId(), playerId))
                .toList();
    }

    public boolean isPlayerAParticipant(String id) {
        return players.stream()
                .map(Participant::player)
                .map(AggregateReference::getId)
                .anyMatch(participantId -> StringUtils.equals(participantId, id));
    }

    public int getPlayersTeam(String playerId) {
        return players.stream()
                .filter(participant -> StringUtils.equals(playerId, participant.player().getId()))
                .findFirst()
                .map(Participant::team)
                .orElse(0);
    }

    public List<Participant> getPlayersFromTeam(int team) {
        if (team < 1 || team > 2) {
            throw new IllegalArgumentException("Team cannot be less than 0 or greater than 1");
        }
        return players.stream()
                .filter(player -> player.team() == team)
                .toList();
    }
}
