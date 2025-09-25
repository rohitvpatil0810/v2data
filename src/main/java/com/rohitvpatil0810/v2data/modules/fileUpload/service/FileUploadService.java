package com.rohitvpatil0810.v2data.modules.fileUpload.service;

import com.rohitvpatil0810.v2data.common.api.exceptions.NotFoundException;
import com.rohitvpatil0810.v2data.modules.cloudflareR2.CloudflareR2Client;
import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileSignedUrlResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileUploadResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.entity.StoredFile;
import com.rohitvpatil0810.v2data.modules.fileUpload.mapper.StoredFileMapper;
import com.rohitvpatil0810.v2data.modules.fileUpload.repository.StoredFileRepository;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private StoredFileRepository storedFileRepository;

    @Autowired
    private StoredFileMapper storedFileMapper;

    public FileUploadResponse uploadFile(MultipartFile uploadFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

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

            cloudflareR2Client.uploadFile(file);

            String fileURL = cloudflareR2Client.generateSignedUrl(file.getName());

            StoredFile fileRecord = StoredFile.builder()
                    .originalFilename(uploadFile.getOriginalFilename())
                    .storageKey(cleanFileName)
                    .user(user)
                    .build();

            storedFileRepository.save(fileRecord);

            FileUploadResponse fileUploadResponse = storedFileMapper.toFileUploadResponse(fileRecord);

            fileUploadResponse.setSignedURL(fileURL);

            return fileUploadResponse;
        } finally {
            file.delete();
        }
    }

    public FileSignedUrlResponse getSignedUrl(Long fileId) throws NotFoundException {
        StoredFile storedFile = getFileByFileId(fileId);

        String signedUrl = cloudflareR2Client.generateSignedUrl(storedFile.getStorageKey());

        FileSignedUrlResponse fileSignedUrlResponse = storedFileMapper.toFileSignedUrlResponse(storedFile);

        fileSignedUrlResponse.setSignedURL(signedUrl);

        return fileSignedUrlResponse;
    }

    public void deleteFile(Long fileId) throws NotFoundException {
        StoredFile file = getFileByFileId(fileId);

        cloudflareR2Client.deleteFile(file.getStorageKey());

        storedFileRepository.delete(file);
    }

    private StoredFile getFileByFileId(Long fileId) throws NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StoredFile storedFile = storedFileRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));

        if (!storedFile.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("File not found");
        }

        return storedFile;
    }
}
