package com.albionrmtempire.proxy;

import com.albionrmtempire.datatransferobject.crystalleague.CrystalLeagueMatchStats;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface AlbionApiClient {

    @GetExchange("/api/gameinfo/matches/crystalleague")
    List<CrystalLeagueMatchStats> crystalLeagueMatches(
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset,
            @RequestParam("category") String category
    );
}
