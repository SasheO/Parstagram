package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
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

public class MainActivityHolder extends AppCompatActivity {
////
////    private static final String TAG = "NewPostActivity";
////    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
////    private File photoFile;
////    public String photoFileName = "photo.jpg";
////    Button btnSubmitPost;
////    EditText etTypeDescription;
////    Button btnTakePicture;
////    ImageView ivPicturePreview;
////    BottomNavigationView bottomNavigationView;
////    List<Post> postList;
////    RecyclerView rvFeed;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////
////
////        btnSubmitPost = findViewById(R.id.btnSubmitPost);
////        etTypeDescription = findViewById(R.id.etTypeDescription);
////        btnTakePicture = findViewById(R.id.btnTakePicture);
////        ivPicturePreview = findViewById(R.id.ivPicturePreview);
////        bottomNavigationView = findViewById(R.id.bottom_navigation);
////        rvFeed = findViewById(R.id.rvFeed);
////
////        // initialize the array that will hold posts and create a PostsAdapter
////        postList = new ArrayList<>();
////
////        // when user tries to take picture for post
////        btnTakePicture.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                launchCamera();
////            }
////        });
////
////        // when user tries to submit post
////        btnSubmitPost.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                submitPost();
////            }
////        });
////
////
////        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
////            @Override
////            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////                switch (item.getItemId()) {
////                    case R.id.actionHomePage:
////                        // todo: do something here
////                        Intent intent = new Intent(MainActivity.this, FeedActivity.class);
////                        startActivity(intent);
////                        return true;
////                    case R.id.actionNewPost:
////                        // todo: do something here
////
////                        return true;
////                    default: return true;
////                }
////            }
////        });
////
////    }
////
////    private void submitPost() {
////        // ensure that user has given a description (error handling)
////        String description = etTypeDescription.getText().toString();
////        if (description.length() > 0){
////            Post newPost = new Post();
////            // get current user
////            ParseUser curr_user = ParseUser.getCurrentUser();
////            newPost.setUser(curr_user);
////            newPost.setDescription(description);
////
////            // save new post to server
////            newPost.saveInBackground(new SaveCallback() {
////                @Override
////                public void done(ParseException e) {
////                    if (e != null){ // if there was an exception returned while trying to save the post to server
////                        Log.e(TAG, "error encountered saving post to server: " + e.toString());
////                        Toast.makeText(MainActivity.this, "sorry! could not upload post. try again...", Toast.LENGTH_SHORT).show();
////
////                    }
////                    else{ // if no error returned while trying to save post to server
////                        etTypeDescription.setText(null); // clear text box
////                        ivPicturePreview.setVisibility(View.GONE); // clear image box
////                        Toast.makeText(MainActivity.this, "posted!", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            });
////
////        }
////        else{ // tell user that they must type in something in the description to make a post
////            Toast.makeText(MainActivity.this, "Please type in a description for this post!", Toast.LENGTH_LONG).show();
////        }
////    }
////
////    // Returns the File for a photo stored on disk given the fileName
////    public File getPhotoFileUri(String fileName) {
////        // Get safe storage directory for photos
////        // Use `getExternalFilesDir` on Context to access package-specific directories.
////        // This way, we don't need to request external read/write runtime permissions.
////        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
////
////        // Create the storage directory if it does not exist
////        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
////            Log.d(TAG, "failed to create directory");
////        }
////
////        // Return the file target for the photo based on filename
////        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
////
////        return file;
////    }
////
////    private void launchCamera() {
////        // guide here: https://guides.codepath.org/android/Accessing-the-Camera-and-Stored-Media
////        // create Intent to take a picture and return control to the calling application
////        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        // Create a File reference for future access
////        photoFile = getPhotoFileUri(photoFileName);
////
////        // wrap File object into a content provider
////        // required for API >= 24
////        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
////        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
////
////        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
////        // So as long as the result is not null, it's safe to use the intent.
////        if (intent.resolveActivity(getPackageManager()) != null) {
////            // Start the image capture intent to take photo
////            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
////        }
////    }
////
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
////            if (resultCode == RESULT_OK) {
////                // by this point we have the camera photo on disk
////                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
////                // RESIZE BITMAP, see section below
////                // Load the taken image into a preview
////                ImageView ivPreview = (ImageView) findViewById(R.id.ivPicturePreview);
////                ivPreview.setImageBitmap(takenImage);
////                ivPreview.setVisibility(View.VISIBLE);
////            } else { // Result was a failure
////                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
////            }
////        }
////    }
////
////
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_main, menu);
////        return true;
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle presses on the action bar items
////        switch (item.getItemId()) {
////            // if user clicks on logout button located in the menu
////            case R.id.miLogout:
////                //todo: logout user
////                ParseUser.logOut();
////                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
////                // go back to the login page after user is logged out
////                goToLoginPage();
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
////    }
////
////    private void goToLoginPage(){
////        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
////        startActivity(intent);
//    }
}