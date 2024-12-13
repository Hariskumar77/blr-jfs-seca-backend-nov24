package com.bosch.stocktoship.entity;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponse {
    User user;

    String message;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

