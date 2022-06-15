package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parstagram.Models.Comment;
import com.example.parstagram.Models.Post;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ComposeCommentActivity extends AppCompatActivity {
    EditText etCommentBody;
    Button btnPostComment;
    public static final String TAG = "ComposeCommentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_comment);

        btnPostComment = (Button) findViewById(R.id.btnPostComment);
        etCommentBody = (EditText) findViewById(R.id.etCommentBody);

        Post post  = getIntent().getParcelableExtra("post");




        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = etCommentBody.getText().toString();
                Log.i(TAG, body);
                // todo: check if comment is valid i.e. it has a body
                if (body.length() == 0){
                    Toast.makeText(ComposeCommentActivity.this, "comment cannot be empty", Toast.LENGTH_LONG).show();
                }
                else{ // send the comment to the server, set body as current user, set post as the post from intent
                    Comment comment = new Comment();
                    comment.setBody(body);
                    comment.setPost(post);
                    comment.setUser(ParseUser.getCurrentUser());
                    comment.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null){
                                Log.e(TAG, "error saving comment to database: " + e.toString());
                            }
                            else {
                                finish();
                            }
                        }
                    });
                }

            }
        });
    }
}