package com.uos.dsd.cinema.utils;

import com.uos.dsd.cinema.core.config.StorageConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class StorageCleaner {

    private static final Logger log = LoggerFactory.getLogger(StorageCleaner.class);
    
    private final Path rootPath;

    public StorageCleaner(StorageConfig storageConfig) {
        this.rootPath = Paths.get(storageConfig.getRootPath()).toAbsolutePath().normalize();
    }

    /**
     * 테스트 환경에서 Storage 디렉토리를 정리합니다.
     */
    public void cleanStorage() {
        try {
            clearAll();
            log.info("Storage directory cleared successfully");
        } catch (Exception e) {
            log.error("Failed to clear storage directory", e);
        }
    }

    /**
     * 전체 storage 디렉토리를 정리합니다. 테스트 환경에서만 사용해야 합니다.
     */
    private void clearAll() {
        try {
            if (Files.exists(rootPath)) {
                Files.walk(rootPath)
                    .sorted((path1, path2) -> path2.compareTo(path1)) // 역순으로 정렬하여 파일부터 삭제
                    .forEach(path -> {
                        try {
                            if (!path.equals(rootPath)) { // root 디렉토리는 삭제하지 않음
                                Files.deleteIfExists(path);
                                log.info("Deleted: {}", path);
                            }
                        } catch (IOException e) {
                            log.warn("Failed to delete: {}", path, e);
                        }
                    });
            }
        } catch (IOException e) {
            log.error("Failed to clear storage directory", e);
        }
    }
} 
