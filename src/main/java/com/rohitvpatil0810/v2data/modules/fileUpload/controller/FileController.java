package com.rohitvpatil0810.v2data.modules.fileUpload.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.api.exceptions.NotFoundException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileSignedUrlResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileUploadResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.service.FileUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;
    private final Tika tika = new Tika();

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileUploadResponse> uploadFile(@RequestPart("audio") @Valid MultipartFile audioFile) throws IOException, BadRequestException {
        if (audioFile == null || audioFile.isEmpty()) {
            throw new BadRequestException("Audio file is required");
        }

        // 1. Quick MIME type check
        String contentType = audioFile.getContentType();
        if (contentType == null || !contentType.startsWith("audio/")) {
            throw new BadRequestException("Invalid file type, only audio files are allowed");
        }

        // 2. Deep inspection with Tika
        String detectedType = tika.detect(audioFile.getInputStream());
        if (!detectedType.startsWith("audio/")) {
            throw new BadRequestException("Invalid file content, only audio files are allowed");
        }

        FileUploadResponse fileUploadResponse = fileUploadService.uploadFile(audioFile);

        return new SuccessResponse<FileUploadResponse>("File uploaded successfully", fileUploadResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<FileSignedUrlResponse> getSignedUrl(@PathVariable("id") Long id) throws NotFoundException {
        FileSignedUrlResponse fileSignedUrlResponse = fileUploadService.getSignedUrl(id);

        return new SuccessResponse<>("Signed Url generated successfully", fileSignedUrlResponse);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteFile(@PathVariable("id") Long id) throws NotFoundException {
        fileUploadService.deleteFile(id);

        return new SuccessResponse("File deleted successfully");
    }
}
