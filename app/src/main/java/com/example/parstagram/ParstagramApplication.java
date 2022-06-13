package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParstagramApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // any network interceptors must be added with the Configuration Builder given this syntax
        builder.networkInterceptors().add(httpLoggingInterceptor);



        // Set applicationId and server based on the values in the Back4App settings.
        // todo: set client key and server
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("A5hMivnv5Qk2samyp4cffljIdQuhrmJikyUrKIDE")
                .clientKey("kqOvb8kkCY5AZ7hCB44Mlpr33D8f7oEb1WSGhs7z")
                .clientBuilder(builder)
                .server("https://parseapi.back4app.com").build());
    }
}
