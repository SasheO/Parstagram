package com.example.parstagram.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.parstagram.Activities.PostDetailsActivity;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends
        RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public static List<Post> postList;
    Context context;

    // constructor to set context
    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.post_item, parent, false);

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



            // todo: if there is a user profile pic, bind the profile pic to ivprofilepic
        }

        @Override
        public void onClick(View v) {
            // todo: implement intent to go to Post details activity, pass in post as intent
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