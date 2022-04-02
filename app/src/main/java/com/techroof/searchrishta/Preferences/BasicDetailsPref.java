package com.techroof.searchrishta.Preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.techroof.searchrishta.R;


public class BasicDetailsPref extends AppCompatActivity {

    private Spinner maritalStatus,spAge,spHeight,spMothertongue,spPhysicalStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details_pref);
        maritalStatus=findViewById(R.id.sp_status);
        spAge=findViewById(R.id.sp_age);
        spHeight=findViewById(R.id.sp_height);
        spMothertongue=findViewById(R.id.sp_mother_tongue);
        spPhysicalStatus=findViewById(R.id.sp_physical_status);

        String[] arraySpinnerMaritalStatus = new String[]{
                "Married", "Single","Divorced"

        };

        String[] arraySpinnerage = new String[]{
                "18", "19","20","21","22","23","24","25","26","27","28","29","30"
                ,"31","32","33","34","35","36","37","38","39"
        };
        String[] arraySpinnerHeight = new String[]{
                "5-1", "5-2","5-3","5-4","5-5","5-6","5-7","5-8","5-9","5-10","5-11"
                ,"6-0","6-1","6-2","6-3","6-4","6-5","6-6","6-7","6-8","6-9","6-10"
                ,"6-11","7-0","7-1"
        };
        String[] arraySpinnerMotherTongue = new String[]{
                "Urdu", "Pashto","Punjabi","English","Siraiki"

        };
        String[] arraySpinnerPhysicalStatus = new String[]{
                "Normal", "AbNormal"

        };


    }
}