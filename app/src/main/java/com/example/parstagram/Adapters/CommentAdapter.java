package com.example.parstagram.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parstagram.Models.Comment;
import com.example.parstagram.Models.Post;
import com.example.parstagram.R;
import com.parse.ParseUser;

import java.util.List;

public class CommentAdapter extends
        RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    Context context;
    List<Comment> commentList;

    // constructor to set context
    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.comment_item, parent, false);

        // Return a new holder instance
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(postView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Comment comment = commentList.get(position);

        holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvCommentUsername;
        public TextView tvCommentBody;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvCommentBody = (TextView) itemView.findViewById(R.id.tvCommentBody);
            tvCommentUsername = (TextView) itemView.findViewById(R.id.tvCommentUsername);
        }

        public void bind(Comment comment) {
            // todo: comment.getUser() returns null object. why?
            // tvCommentUsername.setText(comment.getUser().getUsername());
            tvCommentBody.setText(comment.getBody());
        }
    }

    public void clear() {
        commentList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Comment> list) {
        commentList.addAll(list);
        notifyDataSetChanged();
    }

}
