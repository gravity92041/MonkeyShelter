package com.example.androidspringtestapp.model;

public class RegistrationRequest {
    private String username;
    private String password;
    private String birthYear;

    public RegistrationRequest(String username, String password, String birthYear) {
        this.username = username;
        this.password = password;
        this.birthYear = birthYear;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }
}
