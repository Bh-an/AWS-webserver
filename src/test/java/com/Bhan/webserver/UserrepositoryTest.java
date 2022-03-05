package com.Bhan.webserver;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebserverApplication.class)
public class UserrepositoryTest {

    @Autowired
    Userrepository userrepository;

    @Test
    public void insertUser() {
        Appuser newuser = new Appuser("bhan", "singh", "cbhan.hehe@gmail.com", "passsword");
        userrepository.save(newuser);

        Appuser matchuser = userrepository.finduserbyusername("cbhan.hehe@gmail.com");
        Assert.assertEquals(matchuser.getUsername(), "cbhan");

    }

    @Test
    public void insertDupUser() {
        Appuser newuser = new Appuser("bhan", "singh", "cbhan.hehe@gmail.com", "passsword");
        userrepository.save(newuser);


        long count = userrepository.checkrecords("cbhan.hehe@gmail.com");
        Assert.assertEquals(count, 1);

    }
}