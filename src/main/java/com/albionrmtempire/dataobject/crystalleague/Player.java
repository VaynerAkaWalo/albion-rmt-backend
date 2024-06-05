package com.albionrmtempire.dataobject.crystalleague;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("crystal_player")
public class Player {
    @Id
    private String id;
    @Version
    private String version;
    private String name;
    private int kills;
    private int deaths;
    private int healingDone;
}
