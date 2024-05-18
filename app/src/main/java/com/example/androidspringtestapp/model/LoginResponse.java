package com.example.androidspringtestapp.model;

import java.util.Map;

public class LoginResponse {
    private Map<String,String>tokenMap;

    public Map<String, String> getTokenMap() {
        return tokenMap;
    }

    public void setTokenMap(Map<String, String> tokenMap) {
        this.tokenMap = tokenMap;
    }
    public String getToken(){
        if (tokenMap !=null && tokenMap.containsKey("jwt-token")){
            return tokenMap.get("jwt-token");
        }else {
            return null;
        }

    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "tokenMap=" + tokenMap +
                '}';
    }
}
