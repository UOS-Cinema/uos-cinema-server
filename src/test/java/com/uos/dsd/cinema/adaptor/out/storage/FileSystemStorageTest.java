package com.uos.dsd.cinema.adaptor.out.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileSystemStorageTest {

    private String storagePath = System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString();
    private FileSystemStorage fileSystemStorage;

    public FileSystemStorageTest() {

        this.fileSystemStorage = new FileSystemStorage(storagePath);
    }

    @AfterEach
    void clean() throws IOException {
        
        // Remove all files in the storage path
        Path storageDir = Paths.get(storagePath);
        if (Files.exists(storageDir)) {
            Files.walk(storageDir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete file: " + path, e);
                    }
                });
        }
    }

    @Test
    void testUpload() {
        
        /* Given */
        String fileName = "test.txt";
        String path = "test" + File.separator + "upload" + File.separator + fileName;
        String fileContent = "Hello, World!";
        MultipartFile file = new MockMultipartFile(fileName, fileContent.getBytes());

        /* When */
        fileSystemStorage.upload(path, file);

        /* Then */
        Path filePath = Paths.get(storagePath, path);
        assertTrue(Files.exists(filePath), "업로드된 파일이 존재해야 합니다.");
    }

    @Test
    void testDownload() throws IOException {
        
        /* Given */
        String fileName = "download-test.txt";
        String path = "test" + File.separator + "download" + File.separator + fileName;
        String fileContent = "Hello, Download Test!";
        MultipartFile file = new MockMultipartFile(fileName, fileContent.getBytes());

        // 파일을 먼저 업로드
        fileSystemStorage.upload(path, file);
        Path filePath = Paths.get(storagePath, path);
        assertTrue(Files.exists(filePath), "파일이 업로드되어 존재해야 합니다.");

        /* When */
        Resource downloadedResource = fileSystemStorage.download(path);

        /* Then */
        assertNotNull(downloadedResource, "다운로드된 리소스가 null이 아니어야 합니다.");
        assertTrue(downloadedResource.exists(), "다운로드된 리소스가 존재해야 합니다.");
        assertTrue(downloadedResource.isReadable(), "다운로드된 리소스가 읽기 가능해야 합니다.");
        assertEquals(fileName, downloadedResource.getFilename(), "파일명이 일치해야 합니다.");
        
        // 파일 내용 검증
        try (InputStream inputStream = downloadedResource.getInputStream()) {
            String downloadedContent = new String(inputStream.readAllBytes());
            assertEquals(fileContent, downloadedContent, "파일 내용이 일치해야 합니다.");
        }
    }

    @Test
    void testDownloadNonExistentFile() {

        /* Given */
        String nonExistentPath = "test" + File.separator + "nonexistent.txt";

        /* When & Then */
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileSystemStorage.download(nonExistentPath);
        });
        
        assertTrue(exception.getMessage().contains("File not found or not readable"), 
                    "존재하지 않는 파일에 대한 적절한 예외 메시지가 나와야 합니다.");
    }

    @Test
    void testDelete() {
        /* Given */
        String fileName = "delete-test.txt";
        String path = "test" + File.separator + "delete" + File.separator + fileName;
        String fileContent = "This file will be deleted";
        MultipartFile file = new MockMultipartFile(fileName, fileContent.getBytes());

        fileSystemStorage.upload(path, file);
        Path filePath = Paths.get(storagePath, path);
        assertTrue(Files.exists(filePath), "파일이 업로드되어 존재해야 합니다.");
        
        /* When */
        fileSystemStorage.delete(path);
        
        /* Then */
        assertFalse(Files.exists(filePath), "파일이 삭제되어 존재하지 않아야 합니다.");
    }
}
