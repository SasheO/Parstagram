package com.example.parstagram.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.parstagram.Fragments.FeedFragment;
import com.example.parstagram.Fragments.NewPostFragment;
import com.example.parstagram.Fragments.CurrentUserProfileFragment;
import com.example.parstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    BottomNavigationView bottomNavigationView;
    // these lines below are necessary to be able to refer to the fragments from another fragment via the activity
    final FragmentManager fragmentManager = getSupportFragmentManager();
    public NewPostFragment newPostFragment = new NewPostFragment(MainActivity.this);
    public FeedFragment feedFragment = new FeedFragment();
    public CurrentUserProfileFragment profileFragment = new CurrentUserProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.nav_logo_whiteout);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new FeedFragment());
        ft.commit();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.actionHomePage:
                        // this replaces the fragment housed in frameLayout with a feedfragment
                        fragment = feedFragment;
                        break;
                    case R.id.actionNewPost:
                        // this replaces the fragment housed in frameLayout with a postfragment
                        fragment = newPostFragment;
                        break;

                    case R.id.actionViewProfile:
                        fragment = profileFragment;
                        break;
                    default:
                        fragment = feedFragment;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
                return true;
            }
        });

    }

        // todo: make this function
        public void launchProfileFragment(){
            Fragment fragment = profileFragment;
            fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
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