package com.rohitvpatil0810.v2data.modules.interaction.repository;

import com.rohitvpatil0810.v2data.modules.interaction.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
}
