package com.albionrmtempire.controller.v1;

import com.albionrmtempire.service.crystalleague.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/crystal/player/")
@RequiredArgsConstructor
public class CrystalPlayerController {

    private final PlayerService playerService;

    @GetMapping("{playerName}/teammates")
    Map<String, Long> getPlayerTeammates(@PathVariable("playerName") String playerName) {
        return playerService.getTeammatesByPlayerName(playerName, 10_000);
    }

    @GetMapping("{playerName}/team")
    String getPlayerTeam(@PathVariable("playerName") String playerName) {
        return playerService.getTeamByPlayer(playerName);
    }

}
