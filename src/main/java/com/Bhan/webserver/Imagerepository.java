package com.Bhan.webserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Imagerepository extends CrudRepository<Userimage, String> {

    @Query("select i from images i where i.user_id = ?1")
    public Userimage findimagebyuser_id(String user_id);

    @Query("select count(i) from images i where i.user_id = ?1")
    public long checkrecords(String user_id);

}
