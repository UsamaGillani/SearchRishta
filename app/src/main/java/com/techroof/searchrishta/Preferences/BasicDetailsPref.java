package com.techroof.searchrishta.Preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText etMaritalstatus,etAge,etHeight,etMotherTongue,etPhysicalStatus;
    private String maritalStatus, age, Height, motherTongue, physicalStatus, uId;
    private FirebaseFirestore firestore;
    private Button btnBasicdetails;
    String[] maritalList, ageList, heightList,motherTongueList, physicalStatusList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details_pref);
        uId = FirebaseAuth.getInstance().getUid();
        etMaritalstatus = findViewById(R.id.et_marital_preferences);
        etAge = findViewById(R.id.et_age_preferences);
        etHeight = findViewById(R.id.et_height_preferences);
        etMotherTongue = findViewById(R.id.et_mothertongue_preferences);
        etPhysicalStatus = findViewById(R.id.et_physicalstatus_preferences);
        btnBasicdetails = findViewById(R.id.btn_update_basic_preffer_details);
        firestore = FirebaseFirestore.getInstance();
        maritalList = getResources().getStringArray(R.array.marital_status);
        ageList = getResources().getStringArray(R.array.age);
        heightList = getResources().getStringArray(R.array.height);
        motherTongueList = getResources().getStringArray(R.array.mother_tongue);
        physicalStatusList = getResources().getStringArray(R.array.physical_status);


        etMaritalstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(BasicDetailsPref.this).setTitle("Select Your Basic Preference")
                        .setSingleChoiceItems(maritalList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                etMaritalstatus.setText(maritalList[selectedPosition]);
                                maritalStatus = maritalList[selectedPosition];
                            }
                        })
                        .show();
            }
        });


        etAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(BasicDetailsPref.this).setTitle("Select Your BasicDetails Preferences")
                        .setSingleChoiceItems(ageList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                etAge.setText(ageList[selectedPosition]);
                                age =ageList[selectedPosition];
                            }
                        })
                        .show();
            }
        });


        etMotherTongue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(BasicDetailsPref.this).setTitle("Select MotherTongue Preferences")
                        .setSingleChoiceItems(motherTongueList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                etMotherTongue.setText(heightList[selectedPosition]);
                                Height = heightList[selectedPosition];
                            }
                        })
                        .show();

            }
        });

        etHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(BasicDetailsPref.this).setTitle("Select height Preferences")
                        .setSingleChoiceItems(heightList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                etHeight.setText(heightList[selectedPosition]);
                                Height = heightList[selectedPosition];
                            }
                        })
                        .show();

            }
        });

        etPhysicalStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(BasicDetailsPref.this).setTitle("Select Physical Preferences")
                        .setSingleChoiceItems(physicalStatusList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                etPhysicalStatus.setText(physicalStatusList[selectedPosition]);
                                physicalStatus = physicalStatusList[selectedPosition];
                            }
                        })
                        .show();

            }
        });


        btnBasicdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateBasicDetails(maritalStatus,age,Height,motherTongue,physicalStatus);
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