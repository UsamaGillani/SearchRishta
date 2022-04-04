package com.techroof.searchrishta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.techroof.searchrishta.Adapter.ProfileAdapter;


public class ProfileFragment extends Fragment {

    private RecyclerView rvProfile;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearlayoutmanager;
    private FirebaseAuth mAuth;

    private String[] name = {"Matches", "Mailbox", "Daily Matches", "Chat", "Edit Profile",
            "Edit Partner Preferences"
            , "Contact History", "Upgrade Now", "Privacy Settings", "Notification Settings"
            , "log out"};


    private int[] images = {R.drawable.matches, R.drawable.email, R.drawable.dailymatches, R.drawable.chat,
            R.drawable.chat, R.drawable.chat, R.drawable.chat, R.drawable.chat, R.drawable.chat, R.drawable.chat,
            R.drawable.chat, R.drawable.chat};

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        rvProfile = view.findViewById(R.id.rv_view);
        rvProfile.setHasFixedSize(true);
        linearlayoutmanager = new LinearLayoutManager(getContext());
        rvProfile.setLayoutManager(linearlayoutmanager);
        adapter = new ProfileAdapter(getContext(), name, images);
        rvProfile.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }
}