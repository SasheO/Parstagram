package com.example.parstagram.Models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// this connects this post class to the Post class in your database
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String TAG = "Post";

    // these variables store the name that will be used to send queries to Parse database
    public static final String KEY_DESCRPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKEDBY = "LikedBy";


    public String getDescription(){
        return getString(KEY_DESCRPTION);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public List<ParseUser> getLikedby() {
        if(getList(KEY_LIKEDBY) == null){
        return new ArrayList<>();
    }
        return getList(KEY_LIKEDBY);
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

    public void updateLikedBy(ParseUser current_user){
        List<ParseUser> likedby = getLikedby();
        // todo: change this to hassameid
        for (ParseUser user: likedby){
            if (user.hasSameId(current_user)){
                likedby.remove(user);
                put(KEY_LIKEDBY, likedby);
                Log.i(TAG, likedby.toString());
                return;
            }
        }
        likedby.add(current_user);
        put(KEY_LIKEDBY, likedby);
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
