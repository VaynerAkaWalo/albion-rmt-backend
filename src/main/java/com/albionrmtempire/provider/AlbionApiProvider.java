package com.albionrmtempire.provider;

import com.albionrmtempire.datatransferobject.crystalleague.CrystalLeagueMatchStats;
import com.albionrmtempire.proxy.AlbionApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class AlbionApiProvider {

    private final int STANDARD_OFFSET = 0;
    private final int STANDARD_LIMIT = 50;
    private final String TWENTY_CRYSTAL_LEAGUE_MATCH_TYPE = "crystal_league_city";

    private final AlbionApiClient albionApiClient;

    public List<CrystalLeagueMatchStats> getLatestCrystalMatches() {
        return albionApiClient.crystalLeagueMatches(
                STANDARD_LIMIT,
                STANDARD_OFFSET,
                TWENTY_CRYSTAL_LEAGUE_MATCH_TYPE);
    }
}
