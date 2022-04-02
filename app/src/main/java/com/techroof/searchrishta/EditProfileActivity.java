package com.techroof.searchrishta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.searchrishta.Preferences.PartnerDescription;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {


    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollapsingToolbarLayout toolbarLayout;
    ImageView imgPrfdesc;
    String Uid;
    TextView tvAboutmyself,tvname,dateOfBirth,tvProfileCreated,tvGender,tvHeight, tvMaritalstatus,tvMothertongue,tvPhysicalStatus,tvEducation,tvEmployed,tvOccupation,tvAnnualIncome;
    //private CollapsingToolbarLayout toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        Uid=firebaseAuth.getCurrentUser().getUid();
        toolbar=findViewById(R.id.toolbarr);
        toolbarLayout=findViewById(R.id.collapsing_toolbar);
        tvAboutmyself=findViewById(R.id.tv_about_myself);
        tvname=findViewById(R.id.tv_Name);
        dateOfBirth=findViewById(R.id.tv_Age);
        tvProfileCreated=findViewById(R.id.tv_Profile_created);
        tvGender=findViewById(R.id.tv_gender);
        tvHeight=findViewById(R.id.tv_height);
        tvMaritalstatus=findViewById(R.id.tv_marital_status);
        tvMothertongue=findViewById(R.id.tv_mother_tongue);
        tvPhysicalStatus=findViewById(R.id.tv_physical_status);
        tvEducation=findViewById(R.id.tv_education);
        tvEmployed=findViewById(R.id.tv_employed);
        tvOccupation=findViewById(R.id.tv_occupation);
        tvAnnualIncome=findViewById(R.id.tv_annual_income);
        imgPrfdesc=findViewById(R.id.edit_partner_description);

        imgPrfdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movePartnerdesc=new Intent(getApplicationContext(), PartnerDescription.class);
                startActivity(movePartnerdesc);
            }
        });


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarLayout.setTitle("Edit Profile");

        getdata();




    }

    private void getdata() {

        firestore.collection("users")
                .whereEqualTo("userId", Uid).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String aboutmySelf = document.getString("aboutMe");
                        String citizenship = document.getString("citizenship");
                        String city = document.getString("city");
                        String clan  = document.getString("clan");
                        String country = document.getString("country");
                        String dob = document.getString("dob");
                        String education = document.getString("education");
                        String employedIn = document.getString("employedIn");
                        String ethnicity = document.getString("ethnicity");
                        String gender = document.getString("gender");
                        String height = document.getString("height");
                        String maritalstatus = document.getString("maritalStatus");
                        String motherTongue = document.getString("motherTongue");
                        String name = document.getString("name");
                        String phone = document.getString("phone");
                        String physicalStatus = document.getString("Normal");
                        String profileCreator = document.getString("profileCreator");
                        String relegion = document.getString("relegion");
                        String relegiousValue = document.getString("relegiousValue");
                        String salary = document.getString("salary");
                        String state = document.getString("state");
                        String occupation=document.getString("occupation");

                        //Toast.makeText(getContext(), " id" + uid, Toast.LENGTH_SHORT).show();
                        tvAboutmyself.setText(aboutmySelf);
                        tvname.setText(name);
                        dateOfBirth.setText(dob);
                        tvProfileCreated.setText(profileCreator);
                        tvGender.setText(gender);
                        tvHeight.setText(height);
                        tvMaritalstatus.setText(maritalstatus);
                        tvMothertongue.setText(motherTongue);
                        tvPhysicalStatus.setText(physicalStatus);
                        tvEducation.setText(education);
                        tvEmployed.setText(employedIn);
                        tvOccupation.setText(occupation);
                        tvAnnualIncome.setText(salary);

                    }
                } else {
                    Log.d("d", "Error getting documents: ", task.getException());

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

}