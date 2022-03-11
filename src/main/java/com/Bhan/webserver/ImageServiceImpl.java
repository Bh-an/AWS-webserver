package com.Bhan.webserver;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageStore fileStore;
    private final Imagerepository repository;
    private final Appconfig appconfig;

    @Override
    public Userimage saveImage(String user_id, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Image in the database
        String path = String.format("%s/%s", appconfig.getBucketname(), user_id);
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        Userimage userimage = new Userimage(fileName, path, user_id);
        repository.save(userimage);

        return userimage;
    }

    @Override
    public byte[] downloadImage(String id) {
        Userimage userimage = repository.findById(id).get();
        return fileStore .download(userimage.getUrl(), userimage.getFile_name());
    }
    @Override
    public void deleteImage(String user_id, String filename) {

        fileStore.deleteImage(user_id, filename);
    }

//    @Override
//    public List<Todo> getAllTodos() {
//        List<Todo> todos = new ArrayList<>();
//        repository.findAll().forEach(todos::add);
//        return todos;
//    }
}

