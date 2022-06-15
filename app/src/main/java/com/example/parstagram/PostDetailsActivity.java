package com.example.parstagram;

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

import com.bumptech.glide.Glide;
import com.example.parstagram.Models.Comment;
import com.example.parstagram.Models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    TextView tvTimestamp;
    TextView tvPostUsername;
    TextView tvPostDescription;
    ImageView ivPostImage;
    RecyclerView rvComments;
    ImageButton btnLiked;
    ImageButton btnComment;
    public static final String TAG = "PostDetailsActivity";
    protected CommentAdapter adapter;
    List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvTimestamp = findViewById(R.id.tvTimestamp);
        tvPostDescription = findViewById(R.id.tvPostDescription);
        tvPostUsername = findViewById(R.id.tvPostUsername);
        ivPostImage = findViewById(R.id.ivPostImage);
        rvComments = findViewById(R.id.rvComments);
        btnComment = findViewById(R.id.btnComment);
        btnLiked = findViewById(R.id.btnLiked);
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(PostDetailsActivity.this, commentList);
        // set the adapter and layout manager
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(PostDetailsActivity.this));
        // get the comments and populate it into the recycler view
        queryComments();


        Post post = getIntent().getParcelableExtra("post");

        tvPostDescription.setText(post.getDescription());
        tvPostUsername.setText(post.getUser().getUsername());
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



        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeComment(post);
            }
        });
    }

    private void composeComment(Post post) {
        // todo: this should create an intent to a comment activity that will return a comment
        Intent intent = new Intent(PostDetailsActivity.this, ComposeCommentActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }


    // todo: modify this to query comments
    private void queryComments() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        // include data referred by user key
//        query.include(Post.KEY_USER);
//        // limit query to latest 20 items
//        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null){
                    Log.e(TAG, "error retrieving posts " + e.toString());
                }
                else{
                    // then getting posts from the database was successful\
                    // log the description of each post
                    for (Comment comment: comments){
                        Log.i(TAG, "comment body:" + comment.getBody());
                    }
        //                    adapter.clear();
                            commentList.addAll(comments);
                            adapter.notifyDataSetChanged();
                }
            }
        });
    }
}