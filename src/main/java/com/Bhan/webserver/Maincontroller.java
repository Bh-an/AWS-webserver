package com.Bhan.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;

@RestController
public class Maincontroller {
    @Autowired
    private Userrepository userrepository;

    @GetMapping(path = "/healthz", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Healthzresponse> test() {
        Healthzresponse response = new Healthzresponse("Success");
        return new ResponseEntity<Healthzresponse>(response, HttpStatus.OK);

    }

    @PostMapping(path = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appuser> createuser(@Valid @RequestBody Createuser newuser){
        Appuser user = new Appuser(newuser);
        userrepository.save(user);
        return new ResponseEntity<Appuser>(user, HttpStatus.CREATED);
    }

    @PutMapping(path = "/v1/user/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateuser(@RequestBody Updateuser updateuser, @RequestHeader("Authorization") String authheader){
        String[] authtokens = authheader.split(" ");
        if (authtokens[0].equals("Basic")){
            byte[] decodedBytes = Base64.getDecoder().decode(authtokens[1]);
            String authvalues = new String(decodedBytes);
            String[] authcreds = authvalues.split(":");

            Appuser user = userrepository.finduserbyusername(authcreds[0]);
            boolean fields = false;
            if (user.getPassword().equals(authcreds[1])){
                if (updateuser.getUsername() != null){
                    user.setUsername(updateuser.getUsername());
                    fields = true;
                }
                if (updateuser.getFirst_name() != null){
                    user.setFirst_name(updateuser.getFirst_name());
                    fields = true;

                }
                if (updateuser.getLast_name() != null){
                    user.setLast_name(updateuser.getLast_name());
                    fields = true;

                }
                if (updateuser.getPassword() != null){
                    user.setPassword(updateuser.getPassword());
                    fields = true;
                }
                if (!fields){
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
                user.accountupdate();
                userrepository.save(user);
                return new ResponseEntity<String>("Updated", HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }


    @GetMapping(path = "/v1/user/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showuser(@RequestHeader("Authorization") String authheader){
        String[] authtokens = authheader.split(" ");
        if (authtokens[0].equals("Basic")){
            byte[] decodedBytes = Base64.getDecoder().decode(authtokens[1]);
            String authvalues = new String(decodedBytes);
            String[] authcreds = authvalues.split(":");

            Appuser user = userrepository.finduserbyusername(authcreds[0]);

            if (user.getPassword().equals(authcreds[1])){
                return new ResponseEntity<Appuser>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

}
