package com.example.parstagram.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parstagram.Adapters.FeedPostAdapter;
import com.example.parstagram.Adapters.ProfilePostAdapter;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    RecyclerView rvProfilePosts;
    public List<Post> postList;
    ProfilePostAdapter adapter;

    // required empty constructor
    public ProfileFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postList = new ArrayList<>();
        rvProfilePosts = view.findViewById(R.id.rvProfilePosts);
        adapter = new ProfilePostAdapter(getActivity(), postList);

        // set the adapter on the recycler view
        rvProfilePosts.setAdapter(adapter);
        // set the layout manager on the recycler view as a grid
        rvProfilePosts.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        // query posts from Parstagram
        queryUserPosts(ParseUser.getCurrentUser());
    }

    public void queryUserPosts(ParseUser user) {
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
