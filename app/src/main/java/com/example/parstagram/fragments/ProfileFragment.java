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

import com.example.parstagram.LoginActivity;
import com.example.parstagram.ProfileAdapter;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.List;

public class ProfileFragment extends Fragment {

    TextView tvUsername2;
    ImageView ivProfilePic2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername2 = view.findViewById(R.id.tvUsername2);
        ivProfilePic2 = view.findViewById(R.id.ivProfilePic2);

        ParseUser currentUser = ParseUser.getCurrentUser();

        tvUsername2.setText(currentUser.getUsername());

        if (currentUser.getParseFile("profilePic") != null) {
            Glide.with(this).load(currentUser.getParseFile("profilePic").getUrl()).circleCrop().into(ivProfilePic2);
        }
    }
    }