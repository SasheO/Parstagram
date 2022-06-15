package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    RecyclerView rvFeed;
    List<Post> postList;
    protected PostAdapter adapter;
    public static final String TAG = "FeedActivity";
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        rvFeed = findViewById(R.id.rvFeed);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        postList = new ArrayList<>();
        adapter = new PostAdapter(this, postList);

        // set the adapter on the recycler view
        rvFeed.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvFeed.setLayoutManager(new LinearLayoutManager(this));
        // query posts from Parstagram
        queryPosts();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                queryPosts();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "error retrieving posts " + e.toString());
                }
                else{
                    // then getting posts from the database was successful\
                    // log the description of each post
                    for (Post post : posts){
                        Log.i(TAG, "post description:" + post.getDescription().toString());
                    }
                    adapter.clear();
                    postList.addAll(posts);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    }


