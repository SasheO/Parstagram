package com.example.parstagram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parstagram.Adapters.ProfilePostAdapter;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class OtherUserProfileActivity extends AppCompatActivity {

    ImageView ivProfilePicture;
    TextView tvUsername;
    RecyclerView rvProfilePosts;
    private List<Post> postList;
    ProfilePostAdapter adapter;
    public static final String TAG = "OtherUserProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvUsername = findViewById(R.id.tvUsername);
        rvProfilePosts = findViewById(R.id.rvProfilePosts);
        Post post = getIntent().getParcelableExtra("post");

        postList = new ArrayList<>();

        adapter = new ProfilePostAdapter(OtherUserProfileActivity.this, postList);
        // set the adapter on the recycler view
        rvProfilePosts.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvProfilePosts.setLayoutManager(new LinearLayoutManager(OtherUserProfileActivity.this));
        // query posts from Parstagram
        queryUserPosts(post.getUser());
    }

    private void queryUserPosts(ParseUser user) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // get posts where user is equal to the requested user
        query.whereEqualTo("user", user);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "error retrieving posts: " + e.toString());
                }
                else{
                    // then getting posts from the database was successful\
                    // log the description of each post

                    adapter.clear();
                    postList.addAll(posts);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}