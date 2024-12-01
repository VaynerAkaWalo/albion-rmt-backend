package com.albionrmtempire.provider;

import com.albionrmtempire.datatransferobject.crystalleague.CrystalLeagueMatchStats;
import com.albionrmtempire.proxy.AlbionApiClient;
import com.vaynerakawalo.springobservability.logging.annotation.Egress;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Log4j2
@Component
@Egress(service = "albionApi")
@RequiredArgsConstructor
public class AlbionApiProvider {

    private final int STANDARD_OFFSET = 0;
    private final int STANDARD_LIMIT = 50;
    private final String TWENTY_CRYSTAL_LEAGUE_MATCH_TYPE = "crystal_league_city";

    private final AlbionApiClient albionApiClient;

    @Egress(method = "getLatestCrystalMatches")
    public List<CrystalLeagueMatchStats> getLatestCrystalMatches() {
        return albionApiClient.crystalLeagueMatches(
                STANDARD_LIMIT,
                STANDARD_OFFSET,
                TWENTY_CRYSTAL_LEAGUE_MATCH_TYPE);
    }

    @Egress(method = "getAllCrystalMatches")
    public List<CrystalLeagueMatchStats> getAllMatches() {
        final List<CrystalLeagueMatchStats> allMatches = new LinkedList<>();

        List<CrystalLeagueMatchStats> fetchedMatches;
        int offset = 0;
        do {
            fetchedMatches = albionApiClient.crystalLeagueMatches(
                    STANDARD_LIMIT,
                    offset,
                    TWENTY_CRYSTAL_LEAGUE_MATCH_TYPE
            );
            allMatches.addAll(fetchedMatches);
            offset += STANDARD_LIMIT;
        } while (!fetchedMatches.isEmpty());

        return allMatches;
    }
}
