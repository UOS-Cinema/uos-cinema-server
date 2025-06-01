package com.uos.dsd.cinema.application.port.out.storage;

import org.springframework.web.multipart.MultipartFile;

public interface Storage {
    
    void upload(String path, MultipartFile file);

    String getUrl(String path);

    void delete(String path);
}
