package com.example.parstagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvUsername;
        private ImageView ivProfilePic;
        private ImageView ivPost;
        private TextView tvBottomUsername;
        private TextView tvDescription;
        private TextView tvDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvDetailUsername);
            ivProfilePic = itemView.findViewById(R.id.ivDetailProfilePic);
            ivPost = itemView.findViewById(R.id.ivDetailPost);
            tvBottomUsername = itemView.findViewById(R.id.tvDetailBottomUsername);
            tvDescription = itemView.findViewById(R.id.tvDetailDescription);
            tvDate = itemView.findViewById(R.id.tvDetailDate);
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvBottomUsername.setText(post.getUser().getUsername());
            tvDate.setText(String.format("%s", getRelativeTimeAgo(post.getCreatedAt())));

            if (post.getImage() != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
            }

            if (post.getUser().getParseFile("profilePic") != null) {
                Glide.with(context).load(post.getUser().getParseFile("profilePic").getUrl()).circleCrop().into(ivProfilePic);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            Intent intent = new Intent(context, PostDetailActivity.class);
            Post post = posts.get(position);
            intent.putExtra("post", Parcels.wrap(post));
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public String getRelativeTimeAgo(Date rawJsonDate) {
        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.ENGLISH);
        sf.setLenient(true);

        long time = rawJsonDate.getTime();
        long now = System.currentTimeMillis();

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + "m ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + "h ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + "d";
        }
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
