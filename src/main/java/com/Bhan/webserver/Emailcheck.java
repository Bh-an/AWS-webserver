package com.Bhan.webserver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Emailcheck {

    public static boolean checkemail(String email){

        Pattern pattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches()){
            return true;
        }
        else {
            return false;
        }
    }
}
