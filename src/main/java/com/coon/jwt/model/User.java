package com.coon.jwt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class User {
    @Id // primary
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement
    private long id;
    private String username;
    private String password;
    private String roles; // USER, ADMIN

    public List<String> getRoles() {
        if(this.roles.length() > 0 ){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
