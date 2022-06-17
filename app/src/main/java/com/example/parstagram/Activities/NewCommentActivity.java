package com.example.parstagram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parstagram.Models.Comment;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewCommentActivity extends AppCompatActivity {

    private EditText etTypeComment;
    private Button btnPostComment;
    private static final String TAG = "NewCommentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        btnPostComment = findViewById(R.id.btnPostComment);
        etTypeComment = findViewById(R.id.etTypeComment);

        Post post = getIntent().getParcelableExtra("post");

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = etTypeComment.getText().toString();
                if (body.length() == 0){
                    Toast.makeText(NewCommentActivity.this, "comment cannot be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    Comment newComment = new Comment();
                    newComment.setBody(body);
                    newComment.setUser(ParseUser.getCurrentUser());
                    newComment.setPost(post);
                    newComment.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null){
                                Log.e(TAG, "error saving comment to parse: " + e.toString());
                            }
                            else{
                                Toast.makeText(NewCommentActivity.this, "commented!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}