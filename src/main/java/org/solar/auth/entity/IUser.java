package org.solar.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class IUser extends BEntity{

    String username;

    @OneToMany
    @JoinColumn(name = "iuser_id")
    List<Authorities> authoritiesList = new ArrayList<>();

    @JsonIgnore
    String password;

    @JsonIgnore
    public User getUser(){

        //
        User user = new User(username, password, authoritiesList.stream().map(Authorities::getRole).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

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
