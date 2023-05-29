package org.solar.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Data
@Entity
public class IUser extends BEntity{

    String username;

    @JsonIgnore
    String password;

    @JsonIgnore
    public User getUser(){
        User user = new User(username, password, new ArrayList<>());

        return user;
    }

    String cust;

    public String getCdef(){
        return cust;
    }

    public void setGdef(String value){
        this.cust = value;
    }
}
