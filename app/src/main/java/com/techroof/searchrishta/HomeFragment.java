package com.techroof.searchrishta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techroof.searchrishta.Adapter.HomeFragmentViewPagerAdapter;
import com.techroof.searchrishta.HomeFragments.DashBoardFragment;
import com.techroof.searchrishta.HomeFragments.JustJoinedFragment;
import com.techroof.searchrishta.HomeFragments.MatchesFragment;
import com.techroof.searchrishta.HomeFragments.MutualFragment;
import com.techroof.searchrishta.HomeFragments.NearedByMatchesFragment;
import com.techroof.searchrishta.HomeFragments.PrefferedEducationFragment;
import com.techroof.searchrishta.HomeFragments.PrefferedLocationFragment;
import com.techroof.searchrishta.HomeFragments.PrefferedProfessionFragment;
import com.techroof.searchrishta.HomeFragments.PremiumFragment;
import com.techroof.searchrishta.HomeFragments.ShortListedFragment;
import com.techroof.searchrishta.HomeFragments.ShortlistedMeFragment;
import com.techroof.searchrishta.HomeFragments.ViewedMyProfileFragment;
import com.techroof.searchrishta.HomeFragments.ViewedNotContactedFragment;


public class HomeFragment extends Fragment {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private FirebaseAuth firebaseAuth;
    FirebaseAnalytics mFirebaseAnalytics;
    FirebaseFirestore firebaseFirestore;
    ImageView imgToolbar;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        tableLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        tableLayout.setupWithViewPager(viewPager);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        HomeFragmentViewPagerAdapter viewPagerAdapter = new HomeFragmentViewPagerAdapter(getFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //viewPagerAdapter.addfragment(new ProfileFragment(), "PROFILE");
        viewPagerAdapter.addfragment(new DashBoardFragment(), "DASHBOARD");
        viewPagerAdapter.addfragment(new JustJoinedFragment(), "JUST JOINED");
        viewPagerAdapter.addfragment(new MatchesFragment(), "MATCHES");
        viewPagerAdapter.addfragment(new PremiumFragment(), "PREMIUM");
        viewPagerAdapter.addfragment(new MutualFragment(), "MUTUAL");
        viewPagerAdapter.addfragment(new ViewedNotContactedFragment(), "VIEWED NOT CONTACTED");
        viewPagerAdapter.addfragment(new ViewedMyProfileFragment(), "VIEWED MY PROFILE");
        viewPagerAdapter.addfragment(new ShortlistedMeFragment(), "SHORTLISTED ME");
        viewPagerAdapter.addfragment(new ShortListedFragment(), "SHORTLISTED");
        viewPagerAdapter.addfragment(new NearedByMatchesFragment(), "NEARBY MATCHES");
        viewPagerAdapter.addfragment(new PrefferedProfessionFragment(), "PREFFERED PROFESSION");
        viewPagerAdapter.addfragment(new PrefferedEducationFragment(), "PREFFERED EDUCATION");
        viewPagerAdapter.addfragment(new PrefferedLocationFragment(), "PREFFERED LOCATION");

        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }
}