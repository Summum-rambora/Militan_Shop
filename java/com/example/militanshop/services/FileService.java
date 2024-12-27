package com.example.militanshop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;

    public String saveFile(MultipartFile file) throws IOException {

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }


        String originalFileName = file.getOriginalFilename();


        String safeFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");


        String uniqueFileName = UUID.randomUUID().toString() + "_" + safeFileName;
        Path filePath = Paths.get(uploadPath, uniqueFileName);


        Files.write(filePath, file.getBytes());


        System.out.println("Saving file to: " + filePath.toString());


        return uniqueFileName;
    }
}
