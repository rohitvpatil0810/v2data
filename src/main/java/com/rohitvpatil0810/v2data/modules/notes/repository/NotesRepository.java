package com.rohitvpatil0810.v2data.modules.notes.repository;

import com.rohitvpatil0810.v2data.modules.notes.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
}
