package com.techroof.searchrishta.Preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techroof.searchrishta.R;

import java.util.HashMap;
import java.util.Map;


public class BasicDetailsPref extends AppCompatActivity {

    private Spinner spmaritalStatus, spAge, spHeight, spMothertongue, spPhysicalStatus;
    private String maritalStatus, age, Height, motherTongue, physicalStatus, uId;
    private FirebaseFirestore firestore;
    private Button btnBasicdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details_pref);
        spmaritalStatus = findViewById(R.id.sp_status);
        spAge = findViewById(R.id.sp_age);
        spHeight = findViewById(R.id.sp_height);
        spMothertongue = findViewById(R.id.sp_mother_tongue);
        spPhysicalStatus = findViewById(R.id.sp_physical_status);
        uId = FirebaseAuth.getInstance().getUid();
        btnBasicdetails=findViewById(R.id.btn_update_basic_preffer_details);
        firestore = FirebaseFirestore.getInstance();
        String[] arraySpinnerMaritalStatus = new String[]{
                "Married", "Single", "Divorced"

        };

        String[] arraySpinnerage = new String[]{
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"
                , "31", "32", "33", "34", "35", "36", "37", "38", "39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57," +
                "58,59,60,61,62,63,64,65,66,6,68,69,70"
        };
        String[] arraySpinnerHeight = new String[]{
                "5-1", "5-2", "5-3", "5-4", "5-5", "5-6", "5-7", "5-8", "5-9", "5-10", "5-11"
                , "6-0", "6-1", "6-2", "6-3", "6-4", "6-5", "6-6", "6-7", "6-8", "6-9", "6-10"
                , "6-11", "7-0", "7-1"
        };
        String[] arraySpinnerMotherTongue = new String[]{
                "Urdu", "Pashto", "Punjabi", "English", "Siraiki"

        };
        String[] arraySpinnerPhysicalStatus = new String[]{
                "Normal", "AbNormal"

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerMaritalStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spmaritalStatus.setAdapter(adapter);

        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAge.setAdapter(adapterAge);

        ArrayAdapter<String> adapterHeight = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHeight.setAdapter(adapterHeight);

        ArrayAdapter<String> adapterMothertongue = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHeight.setAdapter(adapterMothertongue);

        ArrayAdapter<String> adapterPhysicalstatus = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHeight.setAdapter(adapterPhysicalstatus);

        spmaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                maritalStatus = spmaritalStatus.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spPhysicalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                physicalStatus = spPhysicalStatus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMothertongue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                motherTongue = spMothertongue.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Height = spHeight.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBasicdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateBasicDetails(maritalStatus, age, Height, motherTongue, physicalStatus);

            }
        });
    }


    private void UpdateBasicDetails(String maritalStatus, String age, String Height, String motherTongue, String physicalStatus) {

        Map<String, Object> userbasicDetailprefMap = new HashMap<>();
        userbasicDetailprefMap.put("maritalStatus", maritalStatus);
        userbasicDetailprefMap.put("age", age);
        userbasicDetailprefMap.put("Height", Height);
        userbasicDetailprefMap.put("motherTongue", motherTongue);
        userbasicDetailprefMap.put("physicalStatus", physicalStatus);

        firestore.collection("users").document(uId).collection("Preferrences").document("preferences")
                .update(userbasicDetailprefMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(getApplicationContext(), "Basic Details Updated Successfully", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}