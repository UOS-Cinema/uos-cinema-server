package com.uos.dsd.cinema.adaptor.out.storage;

import com.uos.dsd.cinema.application.port.out.storage.Storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.MalformedURLException;
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

        String cleanPath = getCleanPath(path);

        // 절대경로로 변환
        Path targetLocation = this.rootPath.resolve(cleanPath);
        targetLocation = targetLocation.toAbsolutePath().normalize();
        log.info("targetLocation: {}", targetLocation);
        log.info("rootPath: {}", this.rootPath);
        if (!targetLocation.startsWith(this.rootPath)) {
            throw new IllegalArgumentException("Cannot store file outside current directory");
        }
        
        try {
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Uploaded file: {}", targetLocation);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + cleanPath, e);
        }
    }

    @Override
    public Resource download(String path) {
        
        String cleanPath = getCleanPath(path);

        // 절대경로로 변환
        Path targetLocation = this.rootPath.resolve(cleanPath).toAbsolutePath().normalize();
        if (!targetLocation.startsWith(this.rootPath)) {
            throw new IllegalArgumentException("Cannot access file outside current directory");
        }

        try {
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists() && resource.isReadable()) {
                log.info("Downloaded file: {}", targetLocation);
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + cleanPath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to create resource for file: " + cleanPath, e);
        }
    }

    @Override
    public void delete(String path) {

        String cleanPath = getCleanPath(path);

        // 절대경로로 변환
        Path targetLocation = this.rootPath.resolve(cleanPath).toAbsolutePath().normalize();
        if (!targetLocation.startsWith(this.rootPath)) {
            throw new IllegalArgumentException("Cannot delete file outside current directory");
        }
        
        try {
            boolean deleted = Files.deleteIfExists(targetLocation);
            log.info("Deleted file: {} {}", targetLocation, deleted ? "successfully" : "failed");
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + cleanPath, e);
        }
    }

    private String getCleanPath(String path) {

        String cleanPath = StringUtils.cleanPath(path);
        if (cleanPath.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence: " + cleanPath);
        }

        return cleanPath;
    }
}
