package com.rohitvpatil0810.v2data.modules.fileUpload.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class FileSignedUrlResponse extends FileUploadResponse {
}
