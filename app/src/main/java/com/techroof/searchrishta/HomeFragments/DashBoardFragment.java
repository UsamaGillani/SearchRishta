package com.techroof.searchrishta.HomeFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.searchrishta.Adapter.DashboardFragmentRecyclerViewAdapter;
import com.techroof.searchrishta.Interfaces.DasboardClickListener;
import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.R;
import com.techroof.searchrishta.Shortlisted;
import com.techroof.searchrishta.ViewModel.ShortlistedViewModel;

import java.util.ArrayList;
import java.util.List;


public class DashBoardFragment extends Fragment implements DasboardClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<Users> userArrayList;
    private DashboardFragmentRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView dashboardRv;
    private FirebaseFirestore firestore;
    private LinearLayoutManager layoutManagerdashboard;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    //private String storedId;
    private ShortlistedViewModel getViewmodel;
    private ArrayList<String> storedId;
    private String uId;
    private FirebaseUser currentFirebaseUser;
    //progress dialog

    private ProgressDialog progressDialog;

    //imageviewNotification
    private ImageView imgViewNotification;

    private TextView tvNotificationCounter;




    public DashBoardFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        dashboardRv = view.findViewById(R.id.rv_dashboard);
        firestore = FirebaseFirestore.getInstance();

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //imageview Initialization



        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();
        //arraylist decleration
        userArrayList = new ArrayList<>();

        storedId = new ArrayList();

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uId=currentFirebaseUser.getUid();
        getViewmodel = new ViewModelProvider(this).get(ShortlistedViewModel.class);
        getViewmodel.getAllshortlisted().observe(getViewLifecycleOwner(), new Observer<List<Shortlisted>>() {
            @Override
            public void onChanged(List<Shortlisted> shortlisteds) {

                for (int i = 0; i < shortlisteds.size(); i++) {


                    storedId.add(shortlisteds.get(i).getUserid());
                    //storedId= String.valueOf(shortlisteds.get(i).getUserid());


                }
                //Toast.makeText(getContext(), ""+storedId.size(), Toast.LENGTH_SHORT).show();

            }
        });
        recyclerViewAdapter = new DashboardFragmentRecyclerViewAdapter(userArrayList,
                requireActivity(), this, storedId);
        //methods
        //getData();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        userArrayList.clear();
        getData();
        recyclerViewAdapter.notifyDataSetChanged();
    }



    private void getData() {

        firestore.collection("users").whereNotEqualTo("userId",uId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    Users listData = documentSnapshot.toObject(Users.class);
                    userArrayList.add(listData);


                }


                layoutManagerdashboard = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
                dashboardRv.setLayoutManager(layoutManagerdashboard);
                /*recyclerViewAdapter = new DashboardFragmentRecyclerViewAdapter(userArrayList,
                        requireActivity(), recyclerViewAdapter.mlistener);*/
                dashboardRv.setAdapter(recyclerViewAdapter);

                progressDialog.dismiss();


            }
        });

    }

    @Override
    public void onItemclickk(String userId, String name, String dob, String height, String relegion, String education, String maritalstatus, String city, String province, String country) {


        // Toast.makeText(getContext(), "yes"+userId+name, Toast.LENGTH_SHORT).show();
        getViewmodel = new ViewModelProvider(this).get(ShortlistedViewModel.class);
        Shortlisted shortlisted = new Shortlisted(userId, name, dob, height, relegion,
                education, maritalstatus, city, province, country);
        getViewmodel.insert(shortlisted);

        /*firestore.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    Users listData = documentSnapshot.toObject(Users.class);
                    userArrayList.add(listData);

                }

                layoutManagerdashboard = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                dashboardRv.setLayoutManager(layoutManagerdashboard);
                recyclerViewAdapter = new DashboardFragmentRecyclerViewAdapter(userArrayList,
                        requireActivity(), recyclerViewAdapter.mlistener);
                dashboardRv.setAdapter(recyclerViewAdapter);


            }
        });*/

    }

    @Override
    public void onRemoveClick(String userId, String name, String dob, String height, String relegion, String education, String maritalstatus,
                              String city, String province, String country) {

        //Toast.makeText(getContext(), "removed" + userId, Toast.LENGTH_SHORT).show();
       /* getViewmodel= new ViewModelProvider(this).get(ShortlistedViewModel.class);
        Shortlisted shortlistedd=new Shortlisted(userId,name,dob,height,relegion,
                education,maritalstatus,city,province,country);*/
        getViewmodel.deletenote(userId);
    }
}