package com.example.parstagram;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;
import java.util.Date;

// this connects this post class to the Post class in your database
@ParseClassName("Post")
public class Post extends ParseObject {

    // these variables store the name that will be used to send queries to Parse database
    public static final String KEY_DESCRPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";


    public String getDescription(){
        return getString(KEY_DESCRPTION);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setDescription(String description){
        put(KEY_DESCRPTION, description);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }

}
