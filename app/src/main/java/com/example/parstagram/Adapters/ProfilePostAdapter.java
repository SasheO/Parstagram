package com.example.parstagram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.ParseFile;

import java.util.List;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {


    public static List<Post> postList;
    Context context;

    // constructor to set context
    public ProfilePostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.profile_post_item, parent, false);

        // Return a new holder instance
        ProfilePostAdapter.ViewHolder viewHolder = new ProfilePostAdapter.ViewHolder(postView);

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
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private ImageView ivProfilePostImage;
//        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            // todo: uncomment and debug below if you want posts to be clickable
            // itemView.setOnClickListener(this);
            ivProfilePostImage = (ImageView) itemView.findViewById(R.id.ivProfilePostImage);

        }

        public void bind(Post post) {
            ParseFile postpic = post.getParseFile("image");
            if (postpic != null){
                Glide.with(context).load(postpic.getUrl()).into(ivProfilePostImage);
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
