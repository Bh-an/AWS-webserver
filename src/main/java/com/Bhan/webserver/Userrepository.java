package com.Bhan.webserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Userrepository extends CrudRepository<Appuser, String> {

    @Query("select u from users u where u.username = ?1")
    public Appuser finduserbyusername(String username);
}
