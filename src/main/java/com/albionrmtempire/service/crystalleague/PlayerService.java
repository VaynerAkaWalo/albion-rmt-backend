package com.albionrmtempire.service.crystalleague;


import com.albionrmtempire.dataobject.crystalleague.CorePlayer;
import com.albionrmtempire.dataobject.crystalleague.Participant;
import com.albionrmtempire.dataobject.crystalleague.Player;
import com.albionrmtempire.datatransferobject.crystalleague.PlayerStats;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.repository.crystalleague.CorePlayersRepository;
import com.albionrmtempire.repository.crystalleague.CrystalLeagueMatchRepository;
import com.albionrmtempire.repository.crystalleague.CrystalPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final CrystalPlayerRepository playerRepository;
    private final CrystalLeagueMatchRepository crystalLeagueMatchRepository;
    private final CorePlayersRepository corePlayersRepository;

    public void saveIfNotExists(Map<String, PlayerStats> players) {
        final Set<Player> existingPlayers = playerRepository.findAllByIdIn(players.keySet());

        final var existingPlayersIds = existingPlayers.stream()
                .map(Player::id)
                .collect(Collectors.toSet());

        final Set<Player> newPlayers = players.entrySet()
                .stream()
                .filter(entry -> !existingPlayersIds.contains(entry.getKey()))
                .map(playerEntry -> createNewPlayer(playerEntry.getKey(), playerEntry.getValue()))
                .collect(Collectors.toSet());

        playerRepository.saveAll(newPlayers);
    }

    private Player createNewPlayer(String id, PlayerStats stats) {
        return new Player(id, null, stats.name());
    }

    public Map<String, Long> getTeammatesByPlayerName(String playerName, int inLastNMatches) {
        final var player = playerRepository.findByNameIgnoreCase(playerName)
                .orElseThrow(() -> new NotFoundException("Could not find player for given name"));

        final var crystalMatches = crystalLeagueMatchRepository.findAllByPlayerIdWithLimit(player.id(), inLastNMatches);

        return crystalMatches.stream()
                .map(match -> match.getTeammatesOfPlayer(player.id()))
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(this::participantToName, Collectors.counting()));
    }

    public String getTeamByPlayer(String playerName) {
        final var teammates = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        teammates.putAll(getTeammatesByPlayerName(playerName, 5));

        final var corePlayers = corePlayersRepository.findAll();

        corePlayers.removeIf(corePlayer -> !teammates.containsKey(corePlayer.name()));

        if (corePlayers.isEmpty()) {
            return "UNKNOWN";
        }

        final var groupedByTeam = corePlayers.stream()
                .collect(Collectors.groupingBy(CorePlayer::team, Collectors.counting()));

        return Collections.max(groupedByTeam.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String getLeaderForTeam(Set<String> playerIds, String current) {
        final var corePlayersInTeam = corePlayersRepository.findAllByIdIn(playerIds);

        final var groupedByTeam = corePlayersInTeam.stream()
                .collect(Collectors.groupingBy(CorePlayer::team, Collectors.counting()));

        if (groupedByTeam.isEmpty()) {
            return current;
        }

        final var highestConfidence = Collections.max(groupedByTeam.entrySet(), Map.Entry.comparingByValue());

        if (highestConfidence.getValue() < 3) {
            return current;
        }

        return corePlayersRepository.findByTeamAndIdInOrderByPriorityDesc(highestConfidence.getKey(), playerIds)
                .stream()
                .map(CorePlayer::id)
                .findFirst()
                .orElse(current);
    }

    private String participantToName(Participant participant) {
        final var player = playerRepository.findById(participant.player().getId())
                .orElseThrow(() -> new NotFoundException("Could not find player for given name"));
        return player.name();
    }
}
