package com.example.parstagram.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.parstagram.Activities.MainActivity;
import com.example.parstagram.Activities.PostDetailsActivity;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class FeedPostAdapter extends
        RecyclerView.Adapter<FeedPostAdapter.ViewHolder> {

    public static List<Post> postList;
    // have to set the context as a MainActivity, not a generic context so that we can refer to MainActivity functions later on in the adapter
    MainActivity context;
    public static final String TAG = "FeedPostAdapter";

    // constructor to set context
    public FeedPostAdapter(Context context, List<Post> postList) {
        this.context = (MainActivity) context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.feed_post_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(postView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Post post = postList.get(position);

        holder.bind(post);

        holder.itemView.setClickable(true);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView tvPostUsername;
        private ImageView ivPostImage;
        private TextView tvPostDescription;
        public ImageView ivUserProfilePic;
        public TextView tvLikeCount;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);
            tvPostDescription = (TextView) itemView.findViewById(R.id.tvPostDescription);
            tvPostUsername = (TextView) itemView.findViewById(R.id.tvPostUsername);
            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
            ivUserProfilePic = (ImageView) itemView.findViewById(R.id.ivUserProfilePic);
        }

        private void bind(Post post) {
            // Bind the post data to the view elements
            ivUserProfilePic.setClickable(true);
            tvPostDescription.setText(post.getDescription());
            tvPostUsername.setText(post.getUser().getUsername());
            tvLikeCount.setText(post.getStringNumLikes());
            ParseFile postimage = post.getImage();
            if (postimage != null) {
                Glide.with(context).load(postimage.getUrl()).into(ivPostImage);
            }
            else{
                ivPostImage.setVisibility(View.GONE);
            }
            ParseFile profilepic = post.getUser().getParseFile("ProfilePic");
            if (profilepic != null){
                Glide.with(context).load(profilepic.getUrl()).transform(new CircleCrop()).into(ivUserProfilePic);
            }


            // this is so that if the profile image is clicked, it opens a profile
            // todo: if profile picture is clicked, launch an intent to a profile activity or profile tab
            ivUserProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (post.getUser().hasSameId(ParseUser.getCurrentUser())){
                        Log.i(TAG, "user profile photo clicked is current user");
                        // go to the profile tab. i got this line of code from stack overflow: https://stackoverflow.com/questions/12142255/call-activity-method-from-adapter
                        if (context instanceof MainActivity) {
                            Log.i(TAG, "launching profile fragment");
                            ((MainActivity)context).launchProfileFragment();
                        }
                    }
                    else{
                        // launch an intent to a profile of the user
                    }
//                    Toast.makeText(context, "This profile was clicked", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onClick(View v) {


            // implement intent to go to Post details activity, pass in post as intent
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Post post = postList.get(position);

                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("post", post);
                context.startActivity(intent);
            }
        }

    }

    public void clear() {
        postList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        postList.addAll(list);
        notifyDataSetChanged();
    }

}