package com.rohitvpatil0810.v2data.modules.fileUpload.repository;

import com.rohitvpatil0810.v2data.modules.fileUpload.entity.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {
}
