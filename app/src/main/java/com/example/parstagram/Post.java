package com.example.parstagram;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;

// this connects this post class to the Post class in your database
@ParseClassName("Post")
public class Post extends ParseObject {

    // todo: insert image attribute
    private String description;
    private ParseUser user;

}
