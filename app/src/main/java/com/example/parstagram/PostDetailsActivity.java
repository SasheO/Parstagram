package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.Date;

public class PostDetailsActivity extends AppCompatActivity {

    TextView tvTimestamp;
    TextView tvPostUsername;
    TextView tvPostDescription;
    ImageView ivPostImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvTimestamp = findViewById(R.id.tvTimestamp);
        tvPostDescription = findViewById(R.id.tvPostDescription);
        tvPostUsername = findViewById(R.id.tvPostUsername);
        ivPostImage = findViewById(R.id.ivPostImage);

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
    }
}