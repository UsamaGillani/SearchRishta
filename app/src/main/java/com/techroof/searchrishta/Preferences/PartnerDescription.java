package com.techroof.searchrishta.Preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techroof.searchrishta.R;

import java.util.HashMap;
import java.util.Map;

public class PartnerDescription extends AppCompatActivity {

    private EditText et_desc;
    private String description;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    private Button btnDescription;
    private String uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_description);
        et_desc=findViewById(R.id.text_content);
        firestore=FirebaseFirestore.getInstance();
        btnDescription=findViewById(R.id.btn_add_description);
                uId = FirebaseAuth.getInstance().getUid();
        Toast.makeText(getApplicationContext(), ""+uId, Toast.LENGTH_SHORT).show();
        btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                description=et_desc.getText().toString();
                AddDescription(description);
            }
        });

    }

    private void AddDescription(String desc){

        Map<String, Object> userDescMap = new HashMap<>();
        userDescMap.put("PartnerDescription", desc);

        firestore.collection("users").document(uId).collection("Preferrences").document("preferences")
                .set(userDescMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(getApplicationContext(), "Description added successfully", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });




    }
}