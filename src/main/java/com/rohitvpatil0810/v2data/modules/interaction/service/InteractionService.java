package com.rohitvpatil0810.v2data.modules.interaction.service;

import com.rohitvpatil0810.v2data.modules.fileUpload.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class InteractionService {
    @Autowired
    FileUploadService fileUploadService;

    public String generateNotes(MultipartFile file) throws IOException {
        return fileUploadService.uploadFile(file);
    }
}
