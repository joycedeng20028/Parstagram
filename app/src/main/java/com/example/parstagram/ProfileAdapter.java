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

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    public static final String TAG = "profile";
    private Context context;
    private List<Post> posts;

    public ProfileAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername2;
        private ImageView ivProfilePic2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername2 = itemView.findViewById(R.id.tvUsername2);
            ivProfilePic2 = itemView.findViewById(R.id.ivProfilePic2);
        }

        public void bind(Post post) {
            tvUsername2.setText(post.getUser().getUsername());
            Log.i(TAG, "username: " + post.getUser().getUsername());
            if (post.getUser().getParseFile("profilePic") != null) {
                Glide.with(context).load(post.getUser().getParseFile("profilePic").getUrl()).circleCrop().into(ivProfilePic2);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
