package com.uos.dsd.cinema.adaptor.out.storage;

import com.uos.dsd.cinema.application.port.out.storage.Storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class FileSystemStorage implements Storage {

    private final Path rootPath;

    public FileSystemStorage(@Value("${storage.root.path}") String uploadPath) {

        this.rootPath = Paths.get(uploadPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @Override
    public void upload(String path, MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }
        
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }

        // 안전한 파일명 생성
        String cleanFilename = StringUtils.cleanPath(originalFilename);
        if (cleanFilename.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence: " + cleanFilename);
        }
        
        // 절대경로로 변환
        Path targetLocation = this.rootPath.resolve(path).resolve(cleanFilename);
        targetLocation = targetLocation.toAbsolutePath().normalize();
        if (!targetLocation.startsWith(this.rootPath)) {
            throw new IllegalArgumentException("Cannot store file outside current directory");
        }
        
        try {
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Uploaded file: {}", targetLocation);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + cleanFilename, e);
        }
    }

    @Override
    public void delete(String path) {

        // 절대경로로 변환
        Path targetLocation = this.rootPath.resolve(path).toAbsolutePath().normalize();
        if (!targetLocation.startsWith(this.rootPath)) {
            throw new IllegalArgumentException("Cannot delete file outside current directory");
        }
        
        try {
            boolean deleted = Files.deleteIfExists(targetLocation);
            log.info("Deleted file: {} {}", targetLocation, deleted ? "successfully" : "failed");
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + path, e);
        }
    }
}
