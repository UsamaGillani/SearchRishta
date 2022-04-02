package com.techroof.searchrishta.HomeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.searchrishta.Adapter.DashboardFragmentRecyclerViewAdapter;
import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.R;

import java.util.ArrayList;


public class PrefferedProfessionFragment extends Fragment {


    private static final String TAG = "MainActivity";
    private ArrayList<Users> userArrayList;
    private DashboardFragmentRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView prefferedProffesionRv;
    private FirebaseFirestore firestore;
    private LinearLayoutManager layoutManagerdashboard;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;


    public PrefferedProfessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrefferedProfessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrefferedProfessionFragment newInstance(String param1, String param2) {
        PrefferedProfessionFragment fragment = new PrefferedProfessionFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_preffered_profession, container, false);
        prefferedProffesionRv = view.findViewById(R.id.preffered_proffesion_rv);
        firestore = FirebaseFirestore.getInstance();

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //arraylist decleration
        userArrayList = new ArrayList<>();


        //methods
        //getData();
        return view;
    }
    /*private void getData() {

        firestore.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    Users listData = documentSnapshot.toObject(Users.class);
                    userArrayList.add(listData);

                }

                layoutManagerdashboard = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                prefferedProffesionRv.setLayoutManager(layoutManagerdashboard);
                recyclerViewAdapter = new DashboardFragmentRecyclerViewAdapter(userArrayList,
                        getContext());
                prefferedProffesionRv.setAdapter(recyclerViewAdapter);


            }
        });


    }*/
}