package com.Bhan.webserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Maincontroller {

    @GetMapping(path = "/healthz", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Healthzresponse> test() {
        Healthzresponse response = new Healthzresponse("Success");
        return new ResponseEntity<Healthzresponse>(response, HttpStatus.OK);

    }

}
