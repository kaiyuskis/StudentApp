package com.example.studentapp.ui.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private PostDAO postDAO;

    public PostAdapter(List<Post> postList, PostDAO postDAO) {
        this.postList = postList;
        this.postDAO = postDAO;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.postText.setText(post.getText());
        holder.postTime.setText(post.getTimestamp());
        holder.likeCount.setText(String.valueOf(post.getLikeCount()));
        if (post.getImageUri() != null) {
            holder.postImage.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(post.getImageUri()).into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }

        holder.likeButton.setOnClickListener(v -> {
            int newLikeCount = post.getLikeCount() + 1;
            post.setLikeCount(newLikeCount);
            postDAO.updateLikeCount(post.getId(), newLikeCount);
            holder.likeCount.setText(String.valueOf(newLikeCount));
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postText;
        ImageView postImage;
        TextView postTime;
        Button likeButton;
        TextView likeCount;

        public PostViewHolder(View itemView) {
            super(itemView);
            postText = itemView.findViewById(R.id.post_text);
            postImage = itemView.findViewById(R.id.post_image);
            postTime = itemView.findViewById(R.id.post_time);
            likeButton = itemView.findViewById(R.id.like_button);
            likeCount = itemView.findViewById(R.id.like_count);
        }
    }
}
