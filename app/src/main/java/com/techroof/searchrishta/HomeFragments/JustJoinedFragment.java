package com.techroof.searchrishta.HomeFragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.searchrishta.Adapter.DashboardFragmentRecyclerViewAdapter;
import com.techroof.searchrishta.Adapter.JustJoinedFragmentRecyclerViewAdapter;
import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JustJoinedFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private ArrayList<Users> userArrayList;
    private JustJoinedFragmentRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView justJoinedrv;
    private FirebaseFirestore firestore;
    private LinearLayoutManager layoutManagerdashboard;
    private FirebaseAuth firebaseAuth;
    private String currentDate;
    //Date dateObj = null;
    FirebaseAuth.AuthStateListener authStateListener;
    public JustJoinedFragment() {
        // Required empty public constructor
    }


    public static JustJoinedFragment newInstance(String param1, String param2) {
        JustJoinedFragment fragment = new JustJoinedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_just_joined, container, false);
        justJoinedrv = view.findViewById(R.id.rv_justjoined);
        firestore = FirebaseFirestore.getInstance();

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //arraylist decleration
        userArrayList = new ArrayList<>();

        /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        currentDate= dtf.format(now);*/
         currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Toast.makeText(getActivity(), ""+currentDate, Toast.LENGTH_LONG).show();

        //methods
        getData();

        return view;
    }

    private void getData() {

        firestore.collection("users").whereEqualTo("dateOfRegistration",currentDate)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    Users listData = documentSnapshot.toObject(Users.class);
                    userArrayList.add(listData);

                }

                layoutManagerdashboard = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                justJoinedrv.setLayoutManager(layoutManagerdashboard);
                recyclerViewAdapter = new JustJoinedFragmentRecyclerViewAdapter(userArrayList,
                        getContext());
                justJoinedrv.setAdapter(recyclerViewAdapter);


            }
        });


    }
}