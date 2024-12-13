package com.bosch.stocktoship.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {


    private String username;
    private String name;
    private String email;
    private String usertype;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDto()
    {}
    public UserDto(String username, String password , String name , String usertype , String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.usertype = usertype;
    }

}
