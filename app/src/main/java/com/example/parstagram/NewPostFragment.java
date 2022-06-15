package com.example.parstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewPostFragment extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.

    private static final String TAG = "NewPostActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    Button btnSubmitPost;
    EditText etTypeDescription;
    Button btnTakePicture;
    ImageView ivPicturePreview;
    BottomNavigationView bottomNavigationView;
    List<Post> postList;
    RecyclerView rvFeed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_new_post, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        btnSubmitPost = view.findViewById(R.id.btnSubmitPost);
        etTypeDescription = view.findViewById(R.id.etTypeDescription);
        btnTakePicture = view.findViewById(R.id.btnTakePicture);
        ivPicturePreview = view.findViewById(R.id.ivPicturePreview);
        // bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        rvFeed = view.findViewById(R.id.rvFeed);

        // initialize the array that will hold posts and create a PostsAdapter
        postList = new ArrayList<>();

        // when user tries to take picture for post
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        // when user tries to submit post
        btnSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

    }

    // todo: edit this to submit post to server
    private void submitPost() {
        // ensure that user has given a description (error handling)
        String description = etTypeDescription.getText().toString();
        if (description.length() > 0){
            Post newPost = new Post();
            // get current user
            ParseUser curr_user = ParseUser.getCurrentUser();
            newPost.setUser(curr_user);
            newPost.setDescription(description);

            // save new post to server
            newPost.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null){ // if there was an exception returned while trying to save the post to server
                        Log.e(TAG, "error encountered saving post to server: " + e.toString());
                        Toast.makeText(getActivity(), "sorry! could not upload post. try again...", Toast.LENGTH_SHORT).show();

                    }
                    else{ // if no error returned while trying to save post to server
                        etTypeDescription.setText(null); // clear text box
                        ivPicturePreview.setVisibility(View.GONE); // clear image box
                        Toast.makeText(getActivity(), "posted!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{ // tell user that they must type in something in the description to make a post
            Toast.makeText(getActivity(), "Please type in a description for this post!", Toast.LENGTH_LONG).show();
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    private void launchCamera() {
        // guide here: https://guides.codepath.org/android/Accessing-the-Camera-and-Stored-Media
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getActivity(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // todo: find out how to return file image to fragment after camera is opened and closed
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                // RESIZE BITMAP, see section below
//                // Load the taken image into a preview
//                ImageView ivPreview = (ImageView) findViewById(R.id.ivPicturePreview);
//                ivPreview.setImageBitmap(takenImage);
//                ivPreview.setVisibility(View.VISIBLE);
//            } else { // Result was a failure
//                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



}