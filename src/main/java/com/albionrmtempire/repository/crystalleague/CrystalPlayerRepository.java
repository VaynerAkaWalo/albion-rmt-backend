package com.albionrmtempire.repository.crystalleague;

import com.albionrmtempire.dataobject.crystalleague.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CrystalPlayerRepository extends CrudRepository<Player, String> {

    Set<Player> findAllByIdIn(Collection<String> ids);

    Optional<Player> findByNameIgnoreCase(String name);
}
