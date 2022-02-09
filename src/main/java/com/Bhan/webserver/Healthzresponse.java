package com.Bhan.webserver;

public class Healthzresponse {

    String message;

    public Healthzresponse(String message) {
        this.message = message;
    }

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
}
