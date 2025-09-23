package com.rohitvpatil0810.v2data.modules.notes.event;

import lombok.Value;

@Value
public class NotesRequestedEvent {
    Long id;
    Long fileId;
    String fileUrl;
}
