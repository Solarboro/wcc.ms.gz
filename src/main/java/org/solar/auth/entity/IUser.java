package org.solar.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Entity
public class IUser extends BEntity implements Serializable {

    String lastname;
    String firstname;
    String username;

    @JsonIgnore
    String password;

    @OneToMany
    @JoinColumn(name="uesrid")
    List<Authorities> authoritiesList = new ArrayList<>();

    @JsonIgnore
    public User getUser(){

        //
        User user = new User(username, password, authoritiesList.stream().map(Authorities::getRole).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return user;
    }

    public String getFullname(){
        return Optional.of(lastname + ", " + firstname).orElse("未知");
    }


}
