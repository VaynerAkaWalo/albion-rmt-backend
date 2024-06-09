package com.albionrmtempire.controller.v1;

import com.albionrmtempire.dataobject.crystalleague.Match;
import com.albionrmtempire.dataobject.crystalleague.Participant;
import com.albionrmtempire.dataobject.crystalleague.Player;
import com.albionrmtempire.datatransferobject.crystalleague.response.CrystalMatchResponse;
import com.albionrmtempire.datatransferobject.crystalleague.response.ParticipantResponse;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.repository.crystalleague.CrystalLeagueMatchRepository;
import com.albionrmtempire.repository.crystalleague.CrystalPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/crystalmatch/20")
@RequiredArgsConstructor
public class CrystalMatchController {

    private final CrystalPlayerRepository playerRepository;
    private final CrystalLeagueMatchRepository crystalLeagueMatchRepository;

    @GetMapping
    public List<CrystalMatchResponse> getAllMatches() {
        final var matches = crystalLeagueMatchRepository.findAll();

        return matches.stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping(params = "playerName")
    public List<CrystalMatchResponse> getAllByPlayer(@RequestParam(value = "playerName") String playerName) {
        final var player = playerRepository.findByName(playerName);
        if (player.isEmpty()) {
            throw new NotFoundException("Could not find player with provided name");
        }

        final var matches = crystalLeagueMatchRepository.findAllByPlayerId(player.get().id());

        return matches.stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping(params = "level")
    public List<CrystalMatchResponse> getAllByLevel(@RequestParam(value = "level") int level) {
        final var matches = crystalLeagueMatchRepository.findAllByLevel(level);

        return matches.stream()
                .map(this::convertToDto)
                .toList();
    }


    private CrystalMatchResponse convertToDto(Match match) {
        final var playerIds = match.players()
                .stream()
                .map(Participant::player)
                .map(AggregateReference::getId)
                .collect(Collectors.toSet());

        final var playerInfo = playerRepository.findAllByIdIn(playerIds)
                .stream()
                .collect(Collectors.toMap(Player::id, Function.identity()));

        return new CrystalMatchResponse(
                match.level(),
                match.winner(),
                match.teamOnePoints(),
                match.teamTwoPoints(),
                match.players().stream().map(participant -> toDto(participant, playerInfo)).toList(),
                match.startTime()
        );
    }

    private ParticipantResponse toDto(Participant participant, Map<String, Player> playerMap) {
        return new ParticipantResponse(
                playerMap.get(participant.player().getId()).name(),
                participant.team());
    }
}
