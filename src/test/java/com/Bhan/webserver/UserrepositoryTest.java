//package com.Bhan.webserver;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = WebserverApplication.class)
//public class UserrepositoryTest {
//
//    @Autowired
//    Userrepository userrepository;
//
//    @Test
//    public void insertUser() {
//        Appuser newuser = new Appuser("xxcbhanxx", "xxsinghxx", "xxcbhan.hehe@gmail.comxx", "xxpassswordxx");
//        userrepository.save(newuser);
//
//        Appuser matchuser = userrepository.finduserbyusername("xxcbhan.hehe@gmail.comxx");
//        Assert.assertEquals(matchuser.getFirst_name(), "xxcbhanxx");
//
//        userrepository.delete(newuser);
//
//    }
//
//    @Test
//    public void insertDupUser() {
//        Appuser newuser = new Appuser("xxcbhanxx", "xxsinghxx", "xxcbhan.hehe@gmail.comxx", "xxpassswordxx");
//        userrepository.save(newuser);
//
//        long count = userrepository.checkrecords("xxcbhan.hehe@gmail.comxx");
//        Assert.assertEquals(count, 1);
//
//        userrepository.delete(newuser);
//
//    }
//
//    @Test
//    public void cleanup() {
//        Appuser newuser = new Appuser("xxbhanxx", "xxsinghxx", "xxcbhan.hehe@gmail.comxx", "xxpassswordxx");
//        userrepository.delete(newuser);
//
//
//        long count = userrepository.checkrecords("cbhan.hehe@gmail.com");
//        Assert.assertEquals(count, 0);
//
//    }
//}