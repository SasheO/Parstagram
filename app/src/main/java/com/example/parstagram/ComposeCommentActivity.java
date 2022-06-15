package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComposeCommentActivity extends AppCompatActivity {
    EditText etCommentBody;
    Button btnPostComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_comment);

        btnPostComment = findViewById(R.id.btnPostComment);
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if comment is valid i.e.
                // send the comment to the server

            }
        });
    }
}