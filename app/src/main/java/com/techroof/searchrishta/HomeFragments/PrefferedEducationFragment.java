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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.searchrishta.Adapter.DashboardFragmentRecyclerViewAdapter;
import com.techroof.searchrishta.Adapter.EducationalPreferenceAdapter;
import com.techroof.searchrishta.Adapter.PrefferedEducationRecyclerViewAdapter;
import com.techroof.searchrishta.Adapter.ProfileCreatorAttributesAdapter;
import com.techroof.searchrishta.Authentication.RegisterActivity;
import com.techroof.searchrishta.Interfaces.ClickListener;
import com.techroof.searchrishta.Interfaces.DasboardClickListener;
import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.R;
import com.techroof.searchrishta.Shortlisted;
import com.techroof.searchrishta.ViewModel.ShortlistedViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PrefferedEducationFragment extends Fragment implements ClickListener, DasboardClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<Users> userArrayList;
    private PrefferedEducationRecyclerViewAdapter recyclerViewAdapter;
    public EducationalPreferenceAdapter educationalPreferenceAdapter;
    RecyclerView prefferedEducationRv, getPrefferedEducationlist;
    private FirebaseFirestore firestore;
    private RecyclerView.LayoutManager layoutManagerdashboard;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private List<String> educationPreferArrayList;
    private RecyclerView.LayoutManager layoutManagereducation;
    public String educationStatus,uId;
    private Button btn;
    public PrefferedEducationFragment prefferedEducationFragment;
    FirebaseUser currentFirebaseUser;
    private ShortlistedViewModel getViewmodel;



    public PrefferedEducationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrefferedEducationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrefferedEducationFragment newInstance(String param1, String param2) {
        PrefferedEducationFragment fragment = new PrefferedEducationFragment();
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
        View view = inflater.inflate(R.layout.fragment_preffered_education, container, false);

        prefferedEducationRv = view.findViewById(R.id.preffered_education_rv);
        getPrefferedEducationlist = view.findViewById(R.id.preffered_education_list);
        educationPreferArrayList = Arrays.asList(getResources().getStringArray(R.array.Education));
        firestore = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uId=currentFirebaseUser.getUid();
        /////////------Profile creator layout manager-------/////////

        layoutManagereducation = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        getPrefferedEducationlist.setHasFixedSize(true);
        getPrefferedEducationlist.setLayoutManager(layoutManagereducation);

        //adapter
        educationalPreferenceAdapter = new EducationalPreferenceAdapter(requireActivity(), educationPreferArrayList, this);
        getPrefferedEducationlist.setAdapter(educationalPreferenceAdapter);

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //arraylist decleration
        userArrayList = new ArrayList<>();
        recyclerViewAdapter = new PrefferedEducationRecyclerViewAdapter(userArrayList,
                getContext(), this);
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationStatus=educationalPreferenceAdapter.educationStatuss;
                Toast.makeText(getContext(), ""+educationStatus, Toast.LENGTH_SHORT).show();
            }
        });*/
        //methods

        //educationStatus= educationalPreferenceAdapter.educationStatuss;
        //Toast.makeText(getContext(), "yes"+educationStatus, Toast.LENGTH_SHORT).show();
        ;

        // getData();
        return view;
    }

   /* public void show() {
        educationStatus = educationalPreferenceAdapter.educationStatuss;
        //Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
        Log.d("www", "show: " + educationStatus);
    }*/

   /* public void getData(String educationStatus) {



        firestore.collection("users").whereEqualTo("education",educationStatus)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    Users listData = documentSnapshot.toObject(Users.class);
                    userArrayList.add(listData);


                }
                Toast.makeText(getContext(), "yes"+educationStatus, Toast.LENGTH_SHORT).show();

                layoutManagerdashboard = new LinearLayoutManager(getContext());
                prefferedEducationRv.setLayoutManager(layoutManagerdashboard);
                recyclerViewAdapter = new DashboardFragmentRecyclerViewAdapter(userArrayList,
                        getContext());
                prefferedEducationRv.setAdapter(recyclerViewAdapter);

            }
        });


    }
*/
    @Override
    public void onItemclick(String click) {

        userArrayList.clear();
        educationStatus = click;
        //Toast.makeText(getContext(), "yes" + uId, Toast.LENGTH_SHORT).show();
        firestore.collection("users").whereEqualTo("education", educationStatus).whereNotEqualTo("userId",uId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    Users listData = documentSnapshot.toObject(Users.class);
                    userArrayList.add(listData);


                }
                //Toast.makeText(getContext(), "yes"+click+userArrayList, Toast.LENGTH_SHORT).show();

                layoutManagerdashboard = new LinearLayoutManager(getContext());
                prefferedEducationRv.setLayoutManager(layoutManagerdashboard);
                prefferedEducationRv.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();


            }
        });




    }

    @Override
    public void onItemclickk(String userId, String name, String dob, String height, String relegion, String education, String maritalstatus, String city, String province, String country) {

        Toast.makeText(getContext(), "yes"+userId+name, Toast.LENGTH_SHORT).show();
        getViewmodel= new ViewModelProvider(this).get(ShortlistedViewModel.class);
        Shortlisted shortlisted=new Shortlisted(userId,name,dob,height,relegion,
                education,maritalstatus,city,province,country);
        getViewmodel.insert(shortlisted);
    }

    @Override
    public void onRemoveClick(String userId, String name, String dob, String height, String relegion, String education, String maritalstatus, String city, String province, String country) {

    }
}
