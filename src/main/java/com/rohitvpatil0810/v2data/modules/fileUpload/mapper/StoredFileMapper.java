package com.rohitvpatil0810.v2data.modules.fileUpload.mapper;

import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileSignedUrlResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileUploadResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.entity.StoredFile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoredFileMapper {

    FileUploadResponse toFileUploadResponse(StoredFile storedFile);

    FileSignedUrlResponse toFileSignedUrlResponse(StoredFile storedFile);
}
