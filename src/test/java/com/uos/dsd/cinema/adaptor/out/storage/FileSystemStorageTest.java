package com.uos.dsd.cinema.adaptor.out.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        String path = "test/upload";
        String fileName = "test.txt";
        String fileContent = "Hello, World!";
        MultipartFile file = new MockMultipartFile("file", fileName, "text/plain", fileContent.getBytes());

        /* When */
        fileSystemStorage.upload(path, file);

        /* Then */
        Path filePath = Paths.get(storagePath, path, fileName);
        assertTrue(Files.exists(filePath), "업로드된 파일이 존재해야 합니다.");
    }

    @Test
    void testDelete() {
        /* Given */
        String path = "test/delete";
        String fileName = "delete-test.txt";
        String fileContent = "This file will be deleted";
        MultipartFile file = new MockMultipartFile("file", fileName, "text/plain", fileContent.getBytes());

        fileSystemStorage.upload(path, file);
        Path filePath = Paths.get(storagePath, path, fileName);
        assertTrue(Files.exists(filePath), "파일이 업로드되어 존재해야 합니다.");
        
        /* When */
        fileSystemStorage.delete(path + File.separator + fileName);
        
        /* Then */
        assertFalse(Files.exists(filePath), "파일이 삭제되어 존재하지 않아야 합니다.");
    }
}
