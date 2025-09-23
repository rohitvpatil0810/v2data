package com.rohitvpatil0810.v2data.modules.fileUpload.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.NotFoundException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileSignedUrlResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileUploadResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileUploadResponse> uploadFile(@RequestPart("audio") MultipartFile audioFile) throws IOException {
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
