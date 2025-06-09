package com.uos.dsd.cinema.adaptor.out.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public class FileSystemStorageTest {

    private String domain = "localhost";
    private String port = "8080";
    private FileSystemStorage fileSystemStorage = new FileSystemStorage(domain, port);

    private String fileName = "test.txt";
    private String uploadPath = "test/upload";

    @AfterEach
    void clean() throws IOException {

        fileSystemStorage.delete(Path.of(uploadPath, fileName).toString());
    }

    @Test
    void testUpload() {

        /* Given */
        String filePath = Path.of(uploadPath, fileName).toString();
        String fileContent = "Hello, World!";
        MultipartFile file = new MockMultipartFile(fileName, fileContent.getBytes());

        /* When */
        fileSystemStorage.upload(filePath, file);

        /* Then */
        assertTrue(fileSystemStorage.exists(filePath));
    }

    @Test
    void testDelete() {

        /* Given */
        String filePath = Path.of(uploadPath, fileName).toString();
        String fileContent = "This file will be deleted";
        MultipartFile file = new MockMultipartFile(fileName, fileContent.getBytes());

        fileSystemStorage.upload(filePath, file);
        assertTrue(fileSystemStorage.exists(filePath));

        /* When */
        fileSystemStorage.delete(filePath);

        /* Then */
        assertFalse(fileSystemStorage.exists(filePath));
    }
}
