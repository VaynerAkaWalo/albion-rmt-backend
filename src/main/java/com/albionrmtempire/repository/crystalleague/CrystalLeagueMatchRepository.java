package com.albionrmtempire.repository.crystalleague;

import com.albionrmtempire.dataobject.crystalleague.Match;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CrystalLeagueMatchRepository extends CrudRepository<Match, String> {

    @Query("select * from crystal_match cm join crystal_player_match p on cm.match_id = p.crystal_match where p.player = :playerId")
    Set<Match> findAllByPlayerId(String playerId);
}
