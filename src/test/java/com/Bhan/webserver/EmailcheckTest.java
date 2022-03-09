package com.Bhan.webserver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

//@SpringBootTest(classes = WebserverApplication.class)

@RunWith(SpringRunner.class)
public class EmailcheckTest {

    @TestConfiguration
    static class EmailcheckTestConfig{
        @Bean
        public Emailcheck emailcheck(){
            return new Emailcheck();
        }
    }
    @Autowired
    private Emailcheck emailcheck;

    @Test
    public void checkemails() {

        String[] emails = {"asdsanskn","bhan@gmail", "hey there", "mynameis$lim$hady@yahoo.com", "hello@gmail.in", "azorath@gmail.com"};
        String[] testdata = {"0","0", "0", "1", "1", "1"};
        String[] traindata = new String[6];
        for(int i = 0; i < emails.length; i++){
            if(!emailcheck.checkemail(emails[i])){
                traindata[i]="0";
            }
            if(emailcheck.checkemail(emails[i])){
                traindata[i]="1";
            }

        }

        Assert.assertEquals(Arrays.toString(traindata), Arrays.toString(testdata));
    }

}
