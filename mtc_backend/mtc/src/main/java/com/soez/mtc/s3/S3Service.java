package com.soez.mtc.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {
    String uploadFile(MultipartFile multipartFile, String kinds) throws IOException;

    void deleteFile(String fileName, String kinds);
}
