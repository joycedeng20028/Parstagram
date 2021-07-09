package com.example.parstagram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.databinding.ItemProfilegridBinding;
import com.parse.ParseFile;
import com.example.parstagram.Post;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    public static final String TAG = "profile";
    private Context context;
    private List<Post> profilePosts;
    ItemProfilegridBinding binding;

    public ProfileAdapter(Context context, List<Post> profilePosts) {
        this.context = context;
        this.profilePosts = profilePosts;
    }


    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProfilegridBinding.inflate(LayoutInflater.from(context));
        return new ProfileAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        Post post = profilePosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return profilePosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfilePost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePost = binding.ivProfilePost;
        }

        public void bind(Post post){
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivProfilePost);
            }
        }
    }
}
