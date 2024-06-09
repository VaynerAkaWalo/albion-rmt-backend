package com.albionrmtempire.service.crystalleague;

import com.albionrmtempire.dataobject.crystalleague.Match;
import com.albionrmtempire.dataobject.crystalleague.Participant;
import com.albionrmtempire.datatransferobject.crystalleague.CrystalLeagueMatchStats;
import com.albionrmtempire.datatransferobject.crystalleague.PlayerStats;
import com.albionrmtempire.repository.crystalleague.CrystalLeagueMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CrystalMatchService {

    private final CrystalLeagueMatchRepository crystalLeagueMatchRepository;
    private final PlayerService playerService;

    public void persistMatchResults(CrystalLeagueMatchStats matchResults) {

        final var alreadyExists = crystalLeagueMatchRepository.existsById(matchResults.matchId());

        if (alreadyExists) {
            return;
        }

        playerService.saveIfNotExists(matchResults.getAllParticipants());

        final var participants = createParticipationRecords(matchResults);

        final var match = new Match(
                matchResults.matchId(),
                null,
                matchResults.winner(),
                matchResults.team1Tickets(),
                matchResults.team2Tickets(),
                matchResults.crystalLeagueLevel(),
                Instant.parse(matchResults.startTime()),
                participants);

        crystalLeagueMatchRepository.save(match);
    }

    private List<Participant> createParticipationRecords(CrystalLeagueMatchStats matchResults) {
        return Stream.concat(
                createParticipationRecordsForTeam(matchResults.team1Results(), 1),
                createParticipationRecordsForTeam(matchResults.team2Results(), 2)
        ).toList();
    }

    private Stream<Participant> createParticipationRecordsForTeam(Map<String, PlayerStats> teamPlayers, int team) {
        return teamPlayers.entrySet()
                .stream()
                .map(player -> new Participant(
                        AggregateReference.to(player.getKey()),
                        team,
                        player.getValue().kills(),
                        player.getValue().deaths(),
                        player.getValue().healingDone()
                ));
    }
}
