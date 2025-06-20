package com.uos.dsd.cinema.application.port.out.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Storage {
    
    void upload(String path, MultipartFile file);

    String getUrl(String path);

    List<String> getUrls(List<String> paths);

    boolean exists(String path);

    void delete(String path);
}
