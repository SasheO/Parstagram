package com.example.parstagram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parstagram.Adapters.CommentAdapter;
import com.example.parstagram.Adapters.PostAdapter;
import com.example.parstagram.Models.Comment;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    TextView tvTimestamp;
    TextView tvPostUsername;
    TextView tvPostDescription;
    ImageView ivPostImage;
    ImageButton btnLiked;
    TextView tvLikeCount;
    ImageButton btnComment;
    RecyclerView rvComments;
    List<Comment> commentList;
    CommentAdapter adapter;
    public static final String TAG = "PostDetailsActivity";
    ParseUser CURRENT_USER = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvTimestamp = findViewById(R.id.tvTimestamp);
        tvPostDescription = findViewById(R.id.tvPostDescription);
        tvPostUsername = findViewById(R.id.tvPostUsername);
        ivPostImage = findViewById(R.id.ivPostImage);
        btnComment = findViewById(R.id.btnComment);
        btnLiked = findViewById(R.id.btnLiked);
        rvComments = findViewById(R.id.rvComments);
        tvLikeCount = findViewById(R.id.tvLikeCount);

        Post post = getIntent().getParcelableExtra("post");
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(PostDetailsActivity.this, commentList);

        // set the adapter on the recycler view
        rvComments.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvComments.setLayoutManager(new LinearLayoutManager(PostDetailsActivity.this));
        // query posts from Parstagram
        queryComments(post);

        tvPostDescription.setText(post.getDescription());
        tvPostUsername.setText(post.getUser().getUsername());
        tvLikeCount.setText(post.getStringNumLikes());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(PostDetailsActivity.this).load(image.getUrl()).into(ivPostImage);
        }
        else{
            ivPostImage.setVisibility(View.GONE);
        }



        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvTimestamp.setText(timeAgo);

        // todo: 3. Ensure that if a post has been liked by a user, it shows on opening a post details activity
        if (post.isLikedBy(CURRENT_USER)){
            btnLiked.setImageResource(R.drawable.ufi_heart_active);
        }
        else {
            btnLiked.setImageResource(R.drawable.ufi_heart);
        }

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailsActivity.this, NewCommentActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });

        btnLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (post.isLikedBy(CURRENT_USER)){
                    btnLiked.setImageResource(R.drawable.ufi_heart);

                }
                else {
                    btnLiked.setImageResource(R.drawable.ufi_heart_active);
                }
                post.updateLikedBy(CURRENT_USER);
                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e!=null){
                            Log.e(TAG, "error liking/unliking image");
                            Toast.makeText(PostDetailsActivity.this, "Was not able to update like", Toast.LENGTH_LONG).show();
                        }
                    }
                });


//                // always like post first
//                btnLiked.setImageResource(R.drawable.ufi_heart_active);
//                // unlike post if user has already liked it
//                for (ParseUser user: post.getLikedby()){
//                    if (user.hasSameId(CURRENT_USER)){
//                        btnLiked.setImageResource(R.drawable.ufi_heart);
//                    }
//                }
//                post.updateLikedBy(CURRENT_USER);
//
//                post.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e != null){
//                            Log.e(TAG, "error liking image: " + e.toString());
//                        }
//
//                    }
//                });

            }
        });
    }

    // todo: edit this comment to only those with the given post
    private void queryComments(Post post) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        // include data where post is current post
        query.whereEqualTo("post", post);
        // this is necessary to include non-primitive types when querying
        query.include("user");
        query.include("post");
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null){
                    Log.e(TAG, "error retrieving comments " + e.toString());
                }
                else{
                    // then getting posts from the database was successful\
                    // log the description of each post
                    for (Comment comment : comments){
                        Log.i(TAG, "post description:" + comment.getBody().toString());
                    }
                    adapter.clear();
                    commentList.addAll(comments);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}