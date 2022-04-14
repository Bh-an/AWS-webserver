package com.Bhan.webserver;

import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.Instant;

@RestController
public class Maincontroller {

    @Autowired
    private SNSService snsservice;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TokenService tokenservice;
    @Autowired
    private Userrepository userrepository;
    @Autowired
    private Imagerepository iamgerepository;
    @Autowired
    private Tokenrepository tokenrepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;
    @Autowired
    private StatsDClient statsd;

    private static final Logger logger = LoggerFactory.getLogger(Maincontroller.class);

    @GetMapping(path = "/healthyboii", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Healthzresponse> test() throws InterruptedException {
        statsd.incrementCounter("server.get.healthz");
        logger.info("Health endpoint called");
        String token = tokenservice.generatetoken(11);
        long timestamp = Instant.now().getEpochSecond();
        timestamp += 30;
        Unverifieduser uvuser = new Unverifieduser(token, tokenservice.generatetoken(10), timestamp);
        tokenrepository.savetoken(uvuser);
        Thread.currentThread().sleep(35000);
        Unverifieduser uvuser1 = tokenrepository.gettoken(token);
        logger.info("UserName: " + uvuser1.getusername());
        logger.info("token: " + uvuser1.gettoken());
        logger.info("expire: " + uvuser1.getexpire());
        Healthzresponse response = new Healthzresponse("Success");
        return new ResponseEntity<Healthzresponse>(response, HttpStatus.OK);

    }

    @PostMapping(path = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appuser> createuser(@Valid @RequestBody Createuser newuser){

        statsd.incrementCounter("server.post.v1/user");
        logger.info("POST /v1/user endpoint called");
        if(!Emailcheck.checkemail(newuser.getUsername())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if(userrepository.checkrecords(newuser.getUsername())!=0){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        long timestamp = Instant.now().getEpochSecond();
        timestamp += 30;
        String token = tokenservice.generatetoken(15);
        logger.info("Token at post request: " + token);
        logger.info("Timestamp at request: " + timestamp);
//      Send username, token and TTL data to DynamoDB
        Unverifieduser uvuser = new Unverifieduser(newuser.getUsername(), token, timestamp);
        tokenrepository.savetoken(uvuser);
        snsservice.sendmessage(newuser.getFirst_name(),newuser.getUsername(),token);
        String password = newuser.getPassword();
        newuser.setPassword(passwordEncoder.encode(password));
        Appuser user = new Appuser(newuser);
        userrepository.save(user);
        return new ResponseEntity<Appuser>(user, HttpStatus.CREATED);
    }

    @PutMapping(path = "/v1/user/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateuser(@RequestBody Updateuser updateuser, @RequestHeader("Authorization") String authheader){
        statsd.incrementCounter("server.put.v1/user/self");
        logger.info("PUT /v1/user/self endpoint called");
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            Appuser user = userrepository.finduserbyusername(authcreds[0]);
            boolean fields = false;
            if (passwordEncoder.matches(authcreds[1], user.getPassword())){
                if (updateuser.getUsername() != null){
                    if(!updateuser.getUsername().matches(user.getUsername())) {
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
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
                    String password = updateuser.getPassword();
                    user.setPassword(passwordEncoder.encode(password));
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

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @GetMapping(path = "/v1/user/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showuser(@RequestHeader("Authorization") String authheader){
        statsd.incrementCounter("server.get.v1/user/self");
        logger.info("GET /v1/user/self endpoint called");
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

    @PostMapping(path = "/v1/user/self/pic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Userimage> uploadimage(@RequestHeader("Authorization") String authheader, @RequestParam("file") MultipartFile image){
        statsd.incrementCounter("server.post.v1/user/self/pic");
        logger.info("POST /v1/user/self/pic endpoint called");
        String[] authcreds = authenticator.getauthcreds(authheader);
        if (authcreds!=null){

            Appuser user = userrepository.finduserbyusername(authcreds[0]);
//            String test = user.getPassword();
            boolean match = passwordEncoder.matches(authcreds[1], user.getPassword());
            if (passwordEncoder.matches(authcreds[1], user.getPassword())){
                if (iamgerepository.checkrecords(user.getId())==1) {

                    Userimage oldimage = iamgerepository.findimagebyuser_id(user.getId());
                    imageService.deleteImage(user.getId(), oldimage.getFile_name());
                    iamgerepository.delete(oldimage);
                }

                return new ResponseEntity<Userimage>(imageService.saveImage(user.getId(), image), HttpStatus.CREATED);

            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(path = "/v1/user/self/pic")
    public ResponseEntity deleteimage(@RequestHeader("Authorization") String authheader){
        statsd.incrementCounter("server.delete.v1/user/self/pic");
        logger.info("DELETE /v1/user/self/pic endpoint called");
        String[] authcreds = authenticator.getauthcreds(authheader);
        if (authcreds!=null){

            Appuser user = userrepository.finduserbyusername(authcreds[0]);
//            String test = user.getPassword();
            boolean match = passwordEncoder.matches(authcreds[1], user.getPassword());
            if (passwordEncoder.matches(authcreds[1], user.getPassword())){
                if (iamgerepository.checkrecords(user.getId())==1) {

                    Userimage image = iamgerepository.findimagebyuser_id(user.getId());
                    imageService.deleteImage(user.getId(), image.getFile_name());
                    iamgerepository.delete(image);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "/v1/user/self/pic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showimage(@RequestHeader("Authorization") String authheader){
        statsd.incrementCounter("server.get.v1/user/self/pic");
        logger.info("GET /v1/user/self/pic endpoint called");
        String[] authcreds = authenticator.getauthcreds(authheader);
        if (authcreds!=null){

            Appuser user = userrepository.finduserbyusername(authcreds[0]);
//            String test = user.getPassword();
            boolean match = passwordEncoder.matches(authcreds[1], user.getPassword());
            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {
                Userimage image = iamgerepository.findimagebyuser_id(user.getId());
                if (image != null) {
                    return new ResponseEntity<Userimage>(image, HttpStatus.OK);
                }
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifytoken(@RequestParam("email") String email, @RequestParam("token") String token){
        Unverifieduser uvuser = tokenrepository.gettoken(email);

        logger.info("Email at get request:" + uvuser.getusername());
        logger.info("token at get request:" + uvuser.gettoken());
        logger.info("expiry at get request:" + uvuser.getexpire());
        if (token.equals(uvuser.gettoken()) && email.equals(uvuser.getusername())){
            Appuser user = userrepository.finduserbyusername(email);
            user.setVerified("yes");
            user.accountupdate();
            userrepository.save(user);

            return new ResponseEntity<String>("Email has been verified !", HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
        // /verification
        // 2 params - username, token
    // check if username exists
    // check if value is same in dynamoDB
    // if true: update SQL as verified
}

