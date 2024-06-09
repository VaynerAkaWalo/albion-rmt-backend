package com.albionrmtempire.service.crystalleague;


import com.albionrmtempire.dataobject.crystalleague.Player;
import com.albionrmtempire.datatransferobject.crystalleague.PlayerStats;
import com.albionrmtempire.repository.crystalleague.CrystalPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final CrystalPlayerRepository playerRepository;

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
}
