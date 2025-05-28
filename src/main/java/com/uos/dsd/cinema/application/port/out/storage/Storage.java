package com.uos.dsd.cinema.application.port.out.storage;

import org.springframework.web.multipart.MultipartFile;

public interface Storage {
    
    void upload(String path, MultipartFile file);

    void delete(String path);
}
