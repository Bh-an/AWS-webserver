package com.Bhan.webserver;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="users")
@Table(name="users")
public class Appuser {
    @Id
    private String id;
    private String first_name;
    private String last_name;
    private String username;
    private LocalDateTime account_created;
    private LocalDateTime account_updated;
    private String verified;
    @JsonIgnore
    private String password;

    public Appuser(String first_name, String last_name, String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.account_created = LocalDateTime.now();
        this.account_updated = LocalDateTime.now();
        this.password = password;
    }

    public Appuser(Createuser newuser) {

        this.id = UUID.randomUUID().toString();
        this.first_name = newuser.getFirst_name();
        this.last_name = newuser.getLast_name();
        this.username = newuser.getUsername();
        this.account_created = LocalDateTime.now();
        this.account_updated = LocalDateTime.now();
        this.password = newuser.getPassword();
        this.verified = "no";
    }

    public Appuser() {
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getFirst_name() {return first_name;}
    public void setFirst_name(String first_name) {this.first_name = first_name;}

    public String getLast_name() {return last_name;}
    public void setLast_name(String last_name) {this.last_name = last_name;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public LocalDateTime getAccount_created() {return account_created;}
    public void setAccount_created(LocalDateTime account_created) {this.account_created = account_created;}

    public LocalDateTime getAccount_updated() {return account_updated;}
    public void setAccount_updated(LocalDateTime account_updated) {this.account_updated = account_updated;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getVerified() {return verified;}
    public void setVerified(String verified) {this.verified = verified;}

    @Override
    public String toString() {
        return "Appuser{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", account_created=" + account_created +
                ", account_updated=" + account_updated +
                '}';
    }

    public void accountupdate(){this.account_updated = LocalDateTime.now();}
}
