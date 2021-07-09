package com.example.parstagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parstagram.Post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parstagram.LoginActivity;
import com.example.parstagram.ProfileAdapter;
import com.example.parstagram.R;
import com.example.parstagram.databinding.FragmentProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    TextView tvUsername2;
    ImageView ivProfilePic2;
    Button btnLogout;
    RecyclerView rvProfilePosts;

    ParseUser currentUser = ParseUser.getCurrentUser();
    List<Post> gridPosts;
    ProfileAdapter adapter;
    FragmentProfileBinding binding;

    private BottomNavigationView bottomNavigationView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername2 = binding.tvUsername2;
        ivProfilePic2 = binding.ivProfilePic2;
        btnLogout = binding.btnLogout;
        rvProfilePosts = binding.rvProfileGrid;
        gridPosts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), gridPosts);

        tvUsername2.setText(currentUser.getUsername());

        if (currentUser.getParseFile("profilePic") != null) {
            Glide.with(this).load(currentUser.getParseFile("profilePic").getUrl()).circleCrop().into(ivProfilePic2);
        }

        rvProfilePosts.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvProfilePosts.setLayoutManager(gridLayoutManager);

        queryPosts();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    return;
                }
                gridPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void logoutUser(){
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        goLoginActivity();
    }

    public void goLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    }