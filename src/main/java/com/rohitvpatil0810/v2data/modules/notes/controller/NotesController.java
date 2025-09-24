package com.rohitvpatil0810.v2data.modules.notes.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.api.exceptions.NotFoundException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequest;
import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequestResponse;
import com.rohitvpatil0810.v2data.modules.notes.dto.NotesWithFileDTO;
import com.rohitvpatil0810.v2data.modules.notes.service.NotesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    NotesService notesService;

    @PostMapping
    ApiResponse<NotesRequestResponse> echoMessage(@RequestBody @Valid NotesRequest notesRequest) throws NotFoundException, BadRequestException {

        NotesRequestResponse notesRequestResponse = notesService.queueNotesRequest(notesRequest);

        return new SuccessResponse<>("Notes request queued for processing", notesRequestResponse);
    }

    @GetMapping
    public Page<NotesWithFileDTO> getNotes(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        return notesService.getNotesByUser(pageable);
    }
}
