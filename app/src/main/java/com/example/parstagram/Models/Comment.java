package com.example.parstagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {

    // these variables store the name that will be used to send queries to Parse database
    public static final String KEY_BODY = "body";
    public static final String KEY_POST = "post";
    public static final String KEY_USER = "user";

    public String getBody(){
        return getString(KEY_BODY);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public ParseFile getPost(){
        return getParseFile(KEY_POST);
    }

    public void setBody(String description){
        put(KEY_BODY, description);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public void setPost(ParseObject post){
        put(KEY_POST, post);
    }

}
