package com.rohitvpatil0810.v2data.modules.notes.repository;

import com.rohitvpatil0810.v2data.modules.notes.dto.NotesWithFileDTO;
import com.rohitvpatil0810.v2data.modules.notes.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotesRepository extends JpaRepository<Notes, Long> {

    @Query("""
                SELECT new com.rohitvpatil0810.v2data.modules.notes.dto.NotesWithFileDTO(
                    n.id,
                    n.notes,
                    n.transcription,
                    n.generatedNotes,
                    n.notesStatus,
                    n.createdAt,
                    f.id,
                    f.originalFilename
                )
                FROM Notes n
                JOIN n.file f
                WHERE f.user.id = :userId
            """)
    Page<NotesWithFileDTO> findNotesByUserId(@Param("userId") Long userId, Pageable pageable);
}
