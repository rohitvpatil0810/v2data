package com.rohitvpatil0810.v2data.modules.notes.mapper;

import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequestResponse;
import com.rohitvpatil0810.v2data.modules.notes.entity.Notes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotesMapper {
    NotesRequestResponse toNotesRequestResponse(Notes notes);
}
