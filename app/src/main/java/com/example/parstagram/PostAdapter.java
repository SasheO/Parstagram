package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvPostUsername;
        public ImageView ivPostImage;
        public TextView tvPostDescription;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvPostDescription = (TextView) itemView.findViewById(R.id.tvPostDescription);
            tvPostUsername = (TextView) itemView.findViewById(R.id.tvPostUsername);
            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
        }

        private void bind(Post post) {
            tvPostDescription.setText(post.getDescription());
            tvPostUsername.setText(post.getUser().getUsername());
            if (post.getImage() != null) {
                // set the image
                Glide.with(context).load(post.getImage().getUrl()).into(ivPostImage);
            }
            else{
                // make the imageView invisible so that it doesn't cause issues in the layout of the page
                ivPostImage.setVisibility(View.GONE);
            }
        }
    }
}