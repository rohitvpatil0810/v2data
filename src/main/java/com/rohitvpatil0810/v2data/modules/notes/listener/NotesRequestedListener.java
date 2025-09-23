package com.rohitvpatil0810.v2data.modules.notes.listener;

import com.rohitvpatil0810.v2data.common.api.exceptions.NotFoundException;
import com.rohitvpatil0810.v2data.modules.notes.event.NotesRequestedEvent;
import com.rohitvpatil0810.v2data.modules.notes.service.NotesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotesRequestedListener {

    private final NotesService notesService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(NotesRequestedEvent notesRequestedEvent) {
        try {
            notesService.generateNotes(notesRequestedEvent);
            log.info("Notes Request Event Processed Successfully - {}", notesRequestedEvent);
        } catch (NotFoundException e) {
            log.error("Failed to process the NotesRequestEvent {} - error: {}", notesRequestedEvent, e.getMessage());
        }
    }
}
