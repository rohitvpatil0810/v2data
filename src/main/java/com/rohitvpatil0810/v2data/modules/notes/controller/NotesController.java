package com.rohitvpatil0810.v2data.modules.notes.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.api.exceptions.NotFoundException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequest;
import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequestResponse;
import com.rohitvpatil0810.v2data.modules.notes.service.NotesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
