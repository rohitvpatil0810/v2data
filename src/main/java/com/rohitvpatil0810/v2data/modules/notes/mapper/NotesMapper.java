package com.rohitvpatil0810.v2data.modules.notes.mapper;

import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequestResponse;
import com.rohitvpatil0810.v2data.modules.notes.entity.Notes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotesMapper {

    @Mapping(target = "fileId", source = "file.id")
    NotesRequestResponse toNotesRequestResponse(Notes notes);
}
