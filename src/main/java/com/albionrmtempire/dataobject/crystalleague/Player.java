package com.albionrmtempire.dataobject.crystalleague;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("crystal_player")
public record Player(@Id String id, @Version Integer version, String name) { }
