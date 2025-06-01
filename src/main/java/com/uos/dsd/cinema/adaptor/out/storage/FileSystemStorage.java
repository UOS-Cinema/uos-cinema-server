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

    private final String host;
    private final String port;
    private final Path rootPath;
    private final String urlPrefix;

    public FileSystemStorage(
        @Value("${spring.server.host}") String host,
        @Value("${spring.server.port}") String port,
        @Value("${storage.url.prefix}") String urlPrefix,
        @Value("${storage.root.path}") String rootPath) {

        this.host = host;
        this.port = port;
        this.urlPrefix = urlPrefix;
        this.rootPath = Paths.get(rootPath).toAbsolutePath().normalize();
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
        String cleanPath = getCleanPath(path);
        Path targetLocation = this.rootPath.resolve(cleanPath).toAbsolutePath().normalize();
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
    public String getUrl(String path) {
        
        return "http://" + host + ":" + port + "/" + urlPrefix + "/" + path;
    }

    @Override
    public void delete(String path) {

        // 절대경로로 변환
        String cleanPath = getCleanPath(path);
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
