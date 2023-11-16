package com.example.dtos;

public class UserResponse {
    private String id;
    private String accessToken;
    public UserResponse(String id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}