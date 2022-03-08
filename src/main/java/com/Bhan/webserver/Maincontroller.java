package com.Bhan.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;

@RestController
public class Maincontroller {



    @Autowired
    private Userrepository userrepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @GetMapping(path = "/healthz", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Healthzresponse> test() {
        Healthzresponse response = new Healthzresponse("Success");
        return new ResponseEntity<Healthzresponse>(response, HttpStatus.OK);

    }

    @PostMapping(path = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appuser> createuser(@Valid @RequestBody Createuser newuser){
        if(!Emailcheck.checkemail(newuser.getUsername())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if(userrepository.checkrecords(newuser.getUsername())!=0){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        String password = newuser.getPassword();
        newuser.setPassword(passwordEncoder.encode(password));
        Appuser user = new Appuser(newuser);
        userrepository.save(user);
        return new ResponseEntity<Appuser>(user, HttpStatus.CREATED);
    }

    @PutMapping(path = "/v1/user/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateuser(@RequestBody Updateuser updateuser, @RequestHeader("Authorization") String authheader){
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){


            Appuser user = userrepository.finduserbyusername(authcreds[0]);
            boolean fields = false;
            if (passwordEncoder.matches(authcreds[1], user.getPassword())){
                if (updateuser.getUsername() != null){
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        }

        return null;
    }


    @GetMapping(path = "/v1/user/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showuser(@RequestHeader("Authorization") String authheader){
        String[] authcreds = authenticator.getauthcreds(authheader);
        if (authcreds!=null){

            Appuser user = userrepository.finduserbyusername(authcreds[0]);
//            String test = user.getPassword();
            boolean match = passwordEncoder.matches(authcreds[1], user.getPassword());
            if (passwordEncoder.matches(authcreds[1], user.getPassword())){
                return new ResponseEntity<Appuser>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
