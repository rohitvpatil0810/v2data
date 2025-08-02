package com.rohitvpatil0810.v2data.modules.fileUpload.dto;

import lombok.Data;

@Data
public class FileUploadResponse {
    private Long id;
    private String originalFilename;
    private String storageKey;
    private String signedURL;
}
