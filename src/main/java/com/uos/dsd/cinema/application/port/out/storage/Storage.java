package com.uos.dsd.cinema.application.port.out.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface Storage {
    
    void upload(String path, MultipartFile file);

    Resource download(String path);

    void delete(String path);
}
