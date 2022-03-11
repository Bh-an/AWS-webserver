package com.Bhan.webserver;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Userimage saveImage(String user_id, MultipartFile file);

    byte[] downloadImage(String id);

    void deleteImage(String user_id, String filename);

//    List<Userimage> getAllImages();
}

