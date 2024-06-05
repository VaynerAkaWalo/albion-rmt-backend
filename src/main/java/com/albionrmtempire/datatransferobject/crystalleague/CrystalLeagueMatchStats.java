package com.albionrmtempire.datatransferobject.crystalleague;

import java.util.Map;

public record CrystalLeagueMatchStats(
        String category,
        String startTime,
        int winner,
        int team1Tickets,
        int team2Tickets,
        String team1LeaderId,
        String team2LeaderId,
        int crystalLeagueLevel,
        Map<String, PlayerStats> team1Results,
        Map<String, PlayerStats> team2Results,
        Object team1Timeline,
        Object team2Timeline,
        String matchId) {}
