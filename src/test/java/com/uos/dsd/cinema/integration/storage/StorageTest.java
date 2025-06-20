package com.uos.dsd.cinema.integration.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.uos.dsd.cinema.application.port.out.storage.Storage;
import com.uos.dsd.cinema.integration.IntegrationTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public class StorageTest extends IntegrationTest {

    @Autowired
    private Storage storage;
    private String fileName = "test.txt";
    private String uploadPath = "test/integration";

    @AfterEach
    void clean() throws IOException {

        storage.delete(Path.of(uploadPath, fileName).toString());
    }

    @Test
    void testUpload() {

        /* Given */
        String filePath = Path.of(uploadPath, fileName).toString();
        String fileContent = "Hello, World!";
        MultipartFile file = new MockMultipartFile(fileName, fileContent.getBytes());

        /* When */
        storage.upload(filePath, file);

        /* Then */
        assertTrue(storage.exists(filePath));
    }

    @Test
    void testDelete() {

        /* Given */
        String filePath = Path.of(uploadPath, fileName).toString();
        String fileContent = "This file will be deleted";
        MultipartFile file = new MockMultipartFile(fileName, fileContent.getBytes());

        storage.upload(filePath, file);
        assertTrue(storage.exists(filePath));

        /* When */
        storage.delete(filePath);

        /* Then */
        assertFalse(storage.exists(filePath));
    }
}
