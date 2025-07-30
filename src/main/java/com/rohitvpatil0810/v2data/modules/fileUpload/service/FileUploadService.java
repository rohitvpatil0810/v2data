package com.rohitvpatil0810.v2data.modules.fileUpload.service;

import com.rohitvpatil0810.v2data.modules.cloudflareR2.CloudflareR2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUploadService {
    @Autowired
    CloudflareR2Client cloudflareR2Client;

    public String uploadFile(MultipartFile uploadFile) throws IOException {
        File file = null;
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new IOException("Failed to create upload directory.");
                }
            }

            String uniqueId = UUID.randomUUID().toString();

            String fileName = uniqueId + "-" + uploadFile.getOriginalFilename();

            String cleanFileName = StringUtils.cleanPath(Objects.requireNonNull(fileName));

            file = new File(uploadDir + cleanFileName);
            uploadFile.transferTo(file);

            cloudflareR2Client.uploadFile("v2data", file);

            String fileURL = cloudflareR2Client.generateSignedUrl("v2data", file.getName());
            return fileURL;
        } finally {
            file.delete();
        }
    }
}
