package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {


    Button btnLogin;
    EditText etTypeUsername;
    EditText etTypePassword;
    String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check if user has already logged in
        if (ParseUser.getCurrentUser() != null){
            // if user is logged in, go directly to main page
            goToMainActivity();
        }


        btnLogin = findViewById(R.id.btnLogin);
        etTypePassword = findViewById(R.id.etTypePassword);
        etTypeUsername = findViewById(R.id.etTypeUsername);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what will happen when user clicks the login button
                String username = etTypeUsername.getText().toString();
                String password = etTypePassword.getText().toString();
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e != null){
                            Log.e(TAG, "issue with login");
                            Toast.makeText(LoginActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Log.d(TAG, "login successful");
                            goToMainActivity();
                        }
                    }
                });

            }
        });
    }

    private void goToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}