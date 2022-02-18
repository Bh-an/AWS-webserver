package com.Bhan.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;


public class Authenticator {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String [] getauthcreds(String authheader){
        String[] authtokens = authheader.split(" ");
        if (authtokens[0].equals("Basic")) {
            byte[] decodedBytes = Base64.getDecoder().decode(authtokens[1]);
            String authvalues = new String(decodedBytes);
            String[] authcreds = authvalues.split(":");
//            String tmp = passwordEncoder.encode(authcreds[1]);
//            authcreds[1] = tmp;

            return authcreds;
        }

        return null;
    }
}
