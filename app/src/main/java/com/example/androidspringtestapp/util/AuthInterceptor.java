package com.example.androidspringtestapp.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = tokenManager.getToken();
        Request originalRequest = chain.request();
        if (token!=null){
            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization","Bearer "+token);
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(originalRequest);
    }
}
