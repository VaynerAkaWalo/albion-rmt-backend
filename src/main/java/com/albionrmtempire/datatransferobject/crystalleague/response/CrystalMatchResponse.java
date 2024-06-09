package com.albionrmtempire.datatransferobject.crystalleague.response;

import java.time.Instant;
import java.util.List;

public record CrystalMatchResponse(
        int level,
        int winner,
        int teamOnePoints,
        int teamTwoPoints,
        String teamOneLeader,
        String teamTwoLeader,
        List<ParticipantResponse> players,
        Instant startTime) { }
