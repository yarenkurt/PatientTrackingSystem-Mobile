package com.example.patienttrackerapp.models;

import java.util.Date;

public class AccountModel {
    public int personId;
    public String fullName;
    public int personType;
    public String token;
    public String refreshToken;
    public Date tokenExpiration;
    public Date refreshTokenExpiration;

    public AccountModel(int personId, String fullName, int personType, String token, String refreshToken, Date tokenExpiration, Date refreshTokenExpiration)
    {
        this.personId = personId;
        this.fullName = fullName;
        this.personType = personType;
        this.token = token;
        this.refreshToken = refreshToken;
        this.tokenExpiration = tokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

}
