package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new FeedFragment());
        ft.commit();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.actionHomePage:
                        // this replaces the fragment housed in frameLayout with a feedfragment
                        ft.replace(R.id.frameLayout, new FeedFragment());
                        ft.commit();
                        return true;
                    case R.id.actionNewPost:
                        // this replaces the fragment housed in frameLayout with a postfragment
                        ft.replace(R.id.frameLayout, new NewPostFragment());
                        ft.commit();
                        return true;
                    case R.id.actionViewProfile:
                        // todo: replace fragment with ViewProfileFragment
//                        ft.replace(R.id.frameLayout, new ViewProfileFragment());
//                        ft.commit();
                        return true;
                    default: return true;
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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}