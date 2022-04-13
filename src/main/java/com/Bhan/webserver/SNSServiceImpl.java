package com.Bhan.webserver;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SNSServiceImpl implements SNSService {

    private final SNSpublish snspublish;

    public void sendmessage(String name, String username, String token){

        String message = String.format("%s,%s,%s",name,username,token);

        snspublish.publishSNSmessage(message);
    }

}
