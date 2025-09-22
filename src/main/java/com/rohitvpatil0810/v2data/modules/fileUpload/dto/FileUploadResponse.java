package com.rohitvpatil0810.v2data.modules.fileUpload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private Long id;
    private String originalFilename;
    private String storageKey;
    private String signedURL;
}
