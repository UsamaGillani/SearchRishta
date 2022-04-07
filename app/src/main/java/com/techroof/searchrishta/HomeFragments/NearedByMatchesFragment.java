package com.techroof.searchrishta.HomeFragments;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.searchrishta.Interfaces.DasboardClickListener;
import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.Adapter.NearByMatchesFragmentRecyclerViewAdapter;
import com.techroof.searchrishta.R;
import com.techroof.searchrishta.Shortlisted;
import com.techroof.searchrishta.ViewModel.ShortlistedViewModel;

import java.util.ArrayList;
import java.util.List;


public class NearedByMatchesFragment extends Fragment implements DasboardClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<Users> userArrayList;
    private NearByMatchesFragmentRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView nearByRv;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private LinearLayoutManager layoutManagerdashboard;
    FirebaseAuth.AuthStateListener authStateListener;
    private ShortlistedViewModel getViewmodel;
    private String longt = null, latt = null;
    private String uId;
    private DasboardClickListener dasboardClickListener;
    private RangeSlider rangeSlider;
    private String startValue, endValue;
    private double st,ed;
    private Button btnFilter;

    private TextView tvCheck;

    public NearedByMatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearedByMatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearedByMatchesFragment newInstance(String param1, String param2) {
        NearedByMatchesFragment fragment = new NearedByMatchesFragment();
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
        View view = inflater.inflate(R.layout.fragment_neared_by_matches, container, false);

        nearByRv = view.findViewById(R.id.nearby_matches_rv);
        firestore = FirebaseFirestore.getInstance();
        rangeSlider = view.findViewById(R.id.range_slider);
        btnFilter=view.findViewById(R.id.btn_filter);

        tvCheck=view.findViewById(R.id.tvcheck);
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> rangeValues;
                rangeValues = slider.getValues();

                startValue = rangeValues.get(0).toString();
                endValue = rangeValues.get(1).toString();

                st=Double.parseDouble(startValue);
                ed=Double.parseDouble(endValue);
                tvCheck.setText(String.valueOf(ed));
                //getcurrentuserData();
               // recyclerViewAdapter = new NearByMatchesFragmentRecyclerViewAdapter(userArrayList,
                 //       getContext(), dasboardClickListener, Double.parseDouble("50.6692533"), Double.parseDouble("80.0741859"),st,ed);
                //nearByRv.setAdapter(recyclerViewAdapter);
                //Toast.makeText(getContext(), "start" + st + "end" + ed, Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        firebaseAuth = FirebaseAuth.getInstance();
        uId = firebaseAuth.getUid();

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getcurrentuserData();
            }
        });
        //arraylist decleration
        userArrayList = new ArrayList<>();


        //getcurrentuserData();


        //methods
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

    private void getData(String currentUserLatt, String currentUserLong) {

        firestore.collection("users").whereNotEqualTo("userId", uId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    Users listData = documentSnapshot.toObject(Users.class);
                    userArrayList.add(listData);

                }

                layoutManagerdashboard = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
                nearByRv.setLayoutManager(layoutManagerdashboard);

                recyclerViewAdapter = new NearByMatchesFragmentRecyclerViewAdapter(userArrayList,
                        getContext(), dasboardClickListener, Double.parseDouble(currentUserLatt), Double.parseDouble(currentUserLong),st,ed);
                nearByRv.setAdapter(recyclerViewAdapter);

            }
        });


    }

    private void getcurrentuserData() {

        if(!userArrayList.isEmpty()){


            userArrayList.clear();
            recyclerViewAdapter.notifyDataSetChanged();
        }
        firestore.collection("users").document(uId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                   /* longt = Double.parseDouble(task.getResult().get("Longitude").toString());
                    latt = Double.parseDouble(task.getResult().get("Latitude").toString());*/
                    longt = (task.getResult().getString("Longitude"));
                    latt = (task.getResult().getString("Latitude"));

                    getData(latt, longt);

                }


            }
        });

    }



}