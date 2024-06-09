package com.albionrmtempire.datatransferobject.crystalleague;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CrystalLeagueMatchStats(
        String startTime,
        int winner,
        int team1Tickets,
        int team2Tickets,
        String team1LeaderId,
        String team2LeaderId,
        int crystalLeagueLevel,
        Map<String, PlayerStats> team1Results,
        Map<String, PlayerStats> team2Results,
        @JsonProperty("MatchId") String matchId) {

    public Map<String, PlayerStats> getAllParticipants() {
        return Stream.concat(team1Results.entrySet().stream(), team2Results.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
