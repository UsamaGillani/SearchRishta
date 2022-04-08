package com.techroof.searchrishta.HomeFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.searchrishta.Adapter.DashboardFragmentRecyclerViewAdapter;
import com.techroof.searchrishta.Adapter.ShortlistedMeRecyclerViewAdapter;
import com.techroof.searchrishta.Interfaces.DasboardClickListener;
import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.R;
import com.techroof.searchrishta.Shortlisted;
import com.techroof.searchrishta.ViewModel.ShortlistedViewModel;

import java.util.ArrayList;


public class ShortlistedMeFragment extends Fragment implements DasboardClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<Users> userArrayList;
    private ShortlistedMeRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView shortlistedmeRv;
    private FirebaseFirestore firestore;
    private LinearLayoutManager layoutManagerdashboard;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private ShortlistedViewModel getViewmodel;
    String uId;
    private ProgressDialog progressDialog;

    public ShortlistedMeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShortlistedMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShortlistedMeFragment newInstance(String param1, String param2) {
        ShortlistedMeFragment fragment = new ShortlistedMeFragment();
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
        View view = inflater.inflate(R.layout.fragment_shortlisted_me, container, false);
        shortlistedmeRv = view.findViewById(R.id.shortlisted_me_rv);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        uId = firebaseAuth.getCurrentUser().getUid();

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //arraylist decleration
        userArrayList = new ArrayList<>();
        recyclerViewAdapter = new ShortlistedMeRecyclerViewAdapter(userArrayList, getContext(), this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //methods
        getData();
        return view;
    }

    @Override
    public void onItemclickk(String userId, String name, String dob, String height, String relegion, String education, String maritalstatus, String city, String province, String country) {

        Toast.makeText(getContext(), "yes" + userId + name, Toast.LENGTH_SHORT).show();
        getViewmodel = new ViewModelProvider(this).get(ShortlistedViewModel.class);
        Shortlisted shortlisted = new Shortlisted(userId, name, dob, height, relegion,
                education, maritalstatus, city, province, country);
        getViewmodel.insert(shortlisted);
    }

    @Override
    public void onRemoveClick(String userId, String name, String dob, String height, String relegion, String education, String maritalstatus, String city, String province, String country) {

    }

    private void getData() {

        Toast.makeText(getContext(), "" + uId, Toast.LENGTH_SHORT).show();
        firestore.collection("SentInterests").whereEqualTo("InterestSent", uId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {


                        String Viewer = document.getString("InterestedPerson");

                        firestore.collection("users").whereEqualTo("userId", Viewer)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    Users listData = documentSnapshot.toObject(Users.class);
                                    userArrayList.add(listData);

                                }

                                layoutManagerdashboard = new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.VERTICAL, false);
                                shortlistedmeRv.setLayoutManager(layoutManagerdashboard);
                                shortlistedmeRv.setAdapter(recyclerViewAdapter);


                                progressDialog.dismiss();
                            }
                        });


                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(),""+e.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }


}

