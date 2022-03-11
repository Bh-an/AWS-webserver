package com.Bhan.webserver;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="images")
@Table(name="images")
public class Userimage {

    @Id
    private String id;
    private String file_name;
    private String url;
    private String user_id;
    private LocalDateTime upload_date;

    public Userimage(String file_name, String url, String user_id) {
        this.id = UUID.randomUUID().toString();
        this.file_name = file_name;
        this.url = url;
        this.user_id = user_id;
        this.upload_date = LocalDateTime.now();
    }

    public Userimage() {

    }

    public String getId() {
        return id;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getUrl() {
        return url;
    }

    public String getUser_id() {
        return user_id;
    }

    public LocalDateTime getUpload_date() {
        return upload_date;
    }
}
