package com.example.patienttrackerapp.models;

import java.util.Date;

public class TokenInfo {
    public String token,refreshToken;
    public Date tokenExpired,refreshTokenExpired;

    public TokenInfo(String token, String refreshToken, Date tokenExpired, Date refreshTokenExpired) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.tokenExpired = tokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
    }
}
