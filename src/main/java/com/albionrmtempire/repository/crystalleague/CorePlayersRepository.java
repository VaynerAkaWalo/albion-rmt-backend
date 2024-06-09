package com.albionrmtempire.repository.crystalleague;

import com.albionrmtempire.dataobject.crystalleague.CorePlayer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CorePlayersRepository extends CrudRepository<CorePlayer, String> {

    Set<CorePlayer> findAll();

    Set<CorePlayer> findAllByIdIn(Set<String> playerIds);

    List<CorePlayer> findByTeamAndIdInOrderByPriorityDesc(String team, Set<String> playerIds);
}
