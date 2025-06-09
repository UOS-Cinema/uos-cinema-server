package com.uos.dsd.cinema.adaptor.out.storage;

import com.uos.dsd.cinema.application.port.out.storage.Storage;
import com.uos.dsd.cinema.common.constant.StorageConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

    private final String domain;
    private final String port;
    private final Path rootPath = Paths.get(StorageConstants.STORAGE_ROOT_PATH).toAbsolutePath().normalize();
    private final String urlPrefix = StorageConstants.STORAGE_URL_PREFIX;

    public FileSystemStorage(
        @Value("${server.host}") String domain,
        @Value("${server.port}") String port) {

        this.domain = domain;
        this.port = port;
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

        // 절대경로로 변환
        Path targetLocation = this.rootPath.resolve(path).toAbsolutePath().normalize();
        if (!targetLocation.startsWith(this.rootPath)) {
            throw new IllegalArgumentException("Cannot store file outside current directory");
        }
        
        try {
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Uploaded file: {}", targetLocation);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + path, e);
        }
    }

    @Override
    public String getUrl(String path) {
        
        return "http://" + domain + ":" + port + "/" + urlPrefix + "/" + path;
    }

    @Override
    public boolean exists(String path) {

        return Files.exists(this.rootPath.resolve(path).toAbsolutePath().normalize());
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
