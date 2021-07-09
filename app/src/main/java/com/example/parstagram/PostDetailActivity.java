package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram.databinding.ActivityPostDetailBinding;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView ivProfilePic;
    ImageView ivPost;
    TextView tvBottomUsername;
    TextView tvDescription;
    TextView tvDate;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        tvUsername = findViewById(R.id.tvDetailUsername);
        ivProfilePic = findViewById(R.id.ivDetailProfilePic);
        ivPost = findViewById(R.id.ivDetailPost);
        tvBottomUsername = findViewById(R.id.tvDetailBottomUsername);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvDate = findViewById(R.id.tvDetailDate);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUsername.setText(currentUser.getUsername());
        tvBottomUsername.setText(currentUser.getUsername());
        tvDescription.setText(post.getDescription());
        tvDate.setText(String.format("%s", getRelativeTimeAgo(post.getCreatedAt())));

        if (currentUser.getParseFile("profilePic") != null) {
            Glide.with(this).load(currentUser.getParseFile("profilePic").getUrl()).circleCrop().into(ivProfilePic);
        }
        if (post.getImage() != null) {
            Glide.with(this).load(post.getImage().getUrl()).into(ivPost);
        }
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

}