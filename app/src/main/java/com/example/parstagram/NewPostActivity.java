package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class NewPostActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        queryPosts();

    }

    private void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER); // not sure what this does, look it up
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "error retrieving posts", e);
                }
                else{
                    // then getting posts from the database was successful\
                    // log the description of each post
                    for (Post post : posts){
                        Log.i(TAG, post.getDescription() + ", username: " + post.getUser().getUsername());
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            // if user clicks on logout button located in the menu
            case R.id.miLogout:
                //todo: logout user
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                // go back to the login page after user is logged out
                goToLoginPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToLoginPage(){
        Intent intent = new Intent(NewPostActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}