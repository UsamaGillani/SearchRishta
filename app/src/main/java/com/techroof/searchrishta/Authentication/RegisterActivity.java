package com.techroof.searchrishta.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techroof.searchrishta.Adapter.GenderAttributeAdapter;
//import com.techroof.searchrishta.Adapter.ProfileCreatorAttributesAdapter;
import com.techroof.searchrishta.Adapter.MaritalStatusAttributeAdapter;
import com.techroof.searchrishta.Adapter.PhysicalStatusAdapter;
import com.techroof.searchrishta.Adapter.ProfileCreatorAttributesAdapter;
import com.techroof.searchrishta.HomeActivity;
import com.techroof.searchrishta.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private View layoutstep1, layoutstep2, layoutsteplogin;

    private RecyclerView genderRv, profileCreatedByRv, maritalStatusRv, physicalStatusRv;
    private ProfileCreatorAttributesAdapter profileCreatorAttributesAdapter;
    private GenderAttributeAdapter genderAttributeAdapter;
    private MaritalStatusAttributeAdapter maritalStatusAttributeAdapter;
    private PhysicalStatusAdapter physicalStatusAdapter;

    private List<String> genderArrayList, profileCreatorArrayList, phoneList, maritalStatusList, physicalStatusList;

    private RecyclerView.LayoutManager layoutManager1, layoutManager2, layoutManager3, layoutManager4;

    private TextInputLayout nameEt, dobEt, emailEt, passwordEt, confirmPassEt, phoneEt, motherTongueEt,
            religionEt, clanEt, countryEt, stateEt, cityEt, citizenshipEt, heightEt, educationEt, employedInEt,
            occupationEt, salaryEt, religiousValueEt, ethnicityEt, aboutMeEt;

    private Button continueBtn1, continueBtn2, continueBtn3;
    private Spinner phoneSpinner;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog pd;
    private TextView loginBtn;
    final Calendar myCalendar = Calendar.getInstance();
    private ConstraintLayout step1Cl, step2Cl, step3Cl;
    private String gender, profileCreator, name, dob, email, password, confirmPass, phone, religion,
            maritalStatus, clan, country, state, city, citizenShip, height, education, employedIn, occupation, salary,
            physicalStatus, religiousValue, ethnicity, aboutMe, motherTongue, activatedstatus = "normal";

    int year, age;
    private String currentDate, strmotherTongue, strRelegionlist, strClanislam, strClanchristian, strClanhindu,
            strClanparsi;
    private String[] motherTonguelist;
    private String[] relegionList;
    private String[] clanIslam;
    private String[] educationalBackground;
    private String[] clanCristian;
    private String[] clanHindu;
    private String[] clanParsi;
    private String[] ethnicityList;
    private String[] countryList;
    private String[] occupationList;
    private String[] heightList;
    private String[] employedInList;
    private String[] nationalityList;
    private String[] relegiousValuesList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        genderArrayList = new ArrayList<>();
        profileCreatorArrayList = new ArrayList<>();
        phoneList = new ArrayList<>();
        maritalStatusList = new ArrayList<>();
        physicalStatusList = new ArrayList<>();
        motherTonguelist = getResources().getStringArray(R.array.mother_tongue);
        relegionList = getResources().getStringArray(R.array.Relegion);
        clanIslam = getResources().getStringArray(R.array.ClanIslam);
        clanCristian = getResources().getStringArray(R.array.ClanChristian);
        clanParsi = getResources().getStringArray(R.array.ClanParsi);
        clanHindu = getResources().getStringArray(R.array.ClanHindu);
        educationalBackground = getResources().getStringArray(R.array.Education);
        genderArrayList = Arrays.asList(getResources().getStringArray(R.array.gender));
        profileCreatorArrayList = Arrays.asList(getResources().getStringArray(R.array.profile_creator));
        phoneList = Arrays.asList(getResources().getStringArray(R.array.phone_number));
        maritalStatusList = Arrays.asList(getResources().getStringArray(R.array.marital_status));
        physicalStatusList = Arrays.asList(getResources().getStringArray(R.array.physical_status));
        ethnicityList = getResources().getStringArray(R.array.Ethnicity);
        countryList = getResources().getStringArray(R.array.countries);
        occupationList = getResources().getStringArray(R.array.Occupation);
        heightList=getResources().getStringArray(R.array.height);
        nationalityList=getResources().getStringArray(R.array.citizenship);
        employedInList=getResources().getStringArray(R.array.employed_in);
        relegiousValuesList=getResources().getStringArray(R.array.RelegiousValues);
        // motherTonguelist = Arrays.asList(getResources().getStringArray(R.array.mother_tongue));
        profileCreatedByRv = findViewById(R.id.profile_created_rv);
        genderRv = findViewById(R.id.gender_rv);
        maritalStatusRv = findViewById(R.id.marital_status_rv);
        physicalStatusRv = findViewById(R.id.physical_status_rv);

        nameEt = findViewById(R.id.name_et);
        dobEt = findViewById(R.id.dob_et);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        confirmPassEt = findViewById(R.id.confirm_password_et);
        phoneEt = findViewById(R.id.phone_et);
        motherTongueEt = findViewById(R.id.mother_tongue_et);
        religionEt = findViewById(R.id.religion_et);
        clanEt = findViewById(R.id.clan_et);
        countryEt = findViewById(R.id.country_et);
        stateEt = findViewById(R.id.state_et);
        cityEt = findViewById(R.id.city_et);
        citizenshipEt = findViewById(R.id.citizenship_et);
        heightEt = findViewById(R.id.height_et);
        educationEt = findViewById(R.id.education_et);
        employedInEt = findViewById(R.id.employed_et);
        occupationEt = findViewById(R.id.occupation_et);
        salaryEt = findViewById(R.id.salary_et);
        religiousValueEt = findViewById(R.id.religious_value_et);
        ethnicityEt = findViewById(R.id.ethnicity_et);
        aboutMeEt = findViewById(R.id.about_me_et);


        phoneSpinner = findViewById(R.id.phone_spinner);

        continueBtn1 = findViewById(R.id.reg_continue_btn);
        continueBtn2 = findViewById(R.id.reg_continue_btn_2);
        continueBtn3 = findViewById(R.id.reg_continue_btn_3);

        layoutstep1 = (View) findViewById(R.id.toolbar_step1);
        layoutstep2 = (View) findViewById(R.id.toolbar_step2);
        layoutsteplogin = (View) findViewById(R.id.toolbar);

        step1Cl = findViewById(R.id.step_1_cl);
        step2Cl = findViewById(R.id.step_2_cl);
        step3Cl = findViewById(R.id.step_3_cl);

        nameEt = findViewById(R.id.name_et);
        dobEt = findViewById(R.id.dob_et);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        confirmPassEt = findViewById(R.id.confirm_password_et);
        phoneEt = findViewById(R.id.phone_et);
        motherTongueEt = findViewById(R.id.mother_tongue_et);
        clanEt = findViewById(R.id.clan_et);
        religionEt = findViewById(R.id.religion_et);
        loginBtn = findViewById(R.id.toolbar_login_Btn);

        loginBtn.setVisibility(View.VISIBLE);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        year = Year.now().getValue();


        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //Toast.makeText(getApplicationContext(), "" + currentDate, Toast.LENGTH_LONG).show();
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait...");

        /////////------Profile creator layout manager-------/////////

        layoutManager1 = new LinearLayoutManager(RegisterActivity.this, RecyclerView.HORIZONTAL, false);
        profileCreatedByRv.setHasFixedSize(true);
        profileCreatedByRv.setLayoutManager(layoutManager1);

        /////////------Gender layout manager-------/////////

        layoutManager2 = new LinearLayoutManager(RegisterActivity.this, RecyclerView.HORIZONTAL, false);
        genderRv.setHasFixedSize(true);
        genderRv.setLayoutManager(layoutManager2);

        /////////------Martial Status layout manager-------/////////

        layoutManager3 = new LinearLayoutManager(RegisterActivity.this, RecyclerView.HORIZONTAL, false);
        maritalStatusRv.setHasFixedSize(true);
        maritalStatusRv.setLayoutManager(layoutManager3);

        /////////------Physical Status layout manager-------/////////

        layoutManager4 = new LinearLayoutManager(RegisterActivity.this, RecyclerView.HORIZONTAL, false);
        physicalStatusRv.setHasFixedSize(true);
        physicalStatusRv.setLayoutManager(layoutManager4);

        /////////----------Layout Adapters-------/////////

        profileCreatorAttributesAdapter = new ProfileCreatorAttributesAdapter(getApplicationContext(), profileCreatorArrayList);
        profileCreatedByRv.setAdapter(profileCreatorAttributesAdapter);

        genderAttributeAdapter = new GenderAttributeAdapter(getApplicationContext(), genderArrayList);
        genderRv.setAdapter(genderAttributeAdapter);

        maritalStatusAttributeAdapter = new MaritalStatusAttributeAdapter(getApplicationContext(), maritalStatusList);
        maritalStatusRv.setAdapter(maritalStatusAttributeAdapter);

        physicalStatusAdapter = new PhysicalStatusAdapter(getApplicationContext(), physicalStatusList);
        physicalStatusRv.setAdapter(physicalStatusAdapter);

        /////////------Phone Spinner-------/////////

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, phoneList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneSpinner.setAdapter(adapter);

        step1Cl.setVisibility(View.VISIBLE);
        step2Cl.setVisibility(View.GONE);
        step3Cl.setVisibility(View.GONE);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        dobEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                age = year - myCalendar.get(Calendar.YEAR);

                Toast.makeText(getApplicationContext(), "" + age, Toast.LENGTH_SHORT).show();
            }
        });


        motherTongueEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Mother Tongue")
                        .setSingleChoiceItems(motherTonguelist, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                motherTongueEt.getEditText().setText(motherTonguelist[selectedPosition]);
                                strmotherTongue = motherTonguelist[selectedPosition];
                                religionEt.requestFocus();
                            }
                        })
                        .show();
            }
        });

        religionEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Relegion")
                        .setSingleChoiceItems(relegionList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                religionEt.getEditText().setText(relegionList[selectedPosition]);
                                strRelegionlist = relegionList[selectedPosition];
                                //clanEt.requestFocus();
                            }
                        })
                        .show();

            }
        });


        clanEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strRelegionlist==null) {

                    Toast.makeText(getApplicationContext(), "please fill above fields", Toast.LENGTH_SHORT).show();

                } else if (strRelegionlist.equals("Christian")) {

                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Clan")
                            .setSingleChoiceItems(clanCristian, 0, null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    dialog.dismiss();

                                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                    clanEt.getEditText().setText(clanCristian[selectedPosition]);
                                    strClanchristian = relegionList[selectedPosition];
                                }
                            })
                            .show();


                    Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                } else if (strRelegionlist.equals("Muslim-Abbasi") || strRelegionlist.equals("Muslim-Brailvi")
                        || strRelegionlist.equals("Muslim-Deobandi") || strRelegionlist.equals("Muslim-Others")
                        || strRelegionlist.equals("Muslim-Shia") || strRelegionlist.equals("Muslim-Wahabi")
                ) {

                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Clan")
                            .setSingleChoiceItems(clanIslam, 0, null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    dialog.dismiss();

                                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                    clanEt.getEditText().setText(clanIslam[selectedPosition]);
                                    //strClanislam = relegionList[selectedPosition];
                                    strClanislam = clanEt.getEditText().toString();
                                    // Toast.makeText(getApplicationContext(), ""+strClanislam, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();

                } else if (strRelegionlist.equals("Hindu")) {

                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Clan")
                            .setSingleChoiceItems(clanHindu, 0, null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    dialog.dismiss();

                                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                    clanEt.getEditText().setText(clanHindu[selectedPosition]);
                                    //strClanhindu = relegionList[selectedPosition];
                                }
                            })
                            .show();


                } else if (strRelegionlist.equals("Parsi")) {

                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Clan")
                            .setSingleChoiceItems(clanParsi, 0, null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    dialog.dismiss();

                                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                    clanEt.getEditText().setText(clanParsi[selectedPosition]);
                                    // strClanparsi = relegionList[selectedPosition];
                                }
                            })
                            .show();


                }
            }
        });

        educationEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Education")
                        .setSingleChoiceItems(educationalBackground, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                educationEt.getEditText().setText(educationalBackground[selectedPosition]);
                                strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();

            }
        });

        ethnicityEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Ethnicity")
                        .setSingleChoiceItems(ethnicityList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                ethnicityEt.getEditText().setText(ethnicityList[selectedPosition]);
                                //strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();

            }
        });

        countryEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Country")
                        .setSingleChoiceItems(countryList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                countryEt.getEditText().setText(countryList[selectedPosition]);
                                //strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();
            }
        });

        occupationEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Occupation")
                        .setSingleChoiceItems(occupationList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                occupationEt.getEditText().setText(occupationList[selectedPosition]);
                                //strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();
            }
        });

        citizenshipEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Nationality")
                        .setSingleChoiceItems(nationalityList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                citizenshipEt.getEditText().setText(nationalityList[selectedPosition]);
                                //strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();

            }
        });

        heightEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Height")
                        .setSingleChoiceItems(heightList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                heightEt.getEditText().setText(heightList[selectedPosition]);
                                //strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();
            }
        });

        employedInEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Employed Status")
                        .setSingleChoiceItems(employedInList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                employedInEt.getEditText().setText(employedInList[selectedPosition]);
                                //strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();
            }
        });

        religiousValueEt.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(RegisterActivity.this).setTitle("Select Your Relegious Values")
                        .setSingleChoiceItems(relegiousValuesList, 0, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                religiousValueEt.getEditText().setText(relegiousValuesList[selectedPosition]);
                                //strClanislam = relegionList[selectedPosition];
                            }
                        })
                        .show();
            }
        });

        continueBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                step1Cl.setVisibility(View.GONE);
                step2Cl.setVisibility(View.VISIBLE);
                step3Cl.setVisibility(View.GONE);

                layoutstep1.setVisibility(View.INVISIBLE);
                layoutstep2.setVisibility(View.VISIBLE);
                layoutsteplogin.setVisibility(View.INVISIBLE);


            }

        });


        continueBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                step1Cl.setVisibility(View.GONE);
                step2Cl.setVisibility(View.GONE);
                step3Cl.setVisibility(View.VISIBLE);

                layoutstep1.setVisibility(View.INVISIBLE);
                layoutstep2.setVisibility(View.INVISIBLE);
                layoutsteplogin.setVisibility(View.VISIBLE);

            }
        });

        continueBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profileCreator = profileCreatorAttributesAdapter.profileCreator;
                gender = genderAttributeAdapter.gender;
                name = nameEt.getEditText().getText().toString();
                dob = dobEt.getEditText().getText().toString();
                email = emailEt.getEditText().getText().toString();
                password = passwordEt.getEditText().getText().toString();
                confirmPass = confirmPassEt.getEditText().getText().toString();
                phone = phoneEt.getEditText().getText().toString();
                motherTongue = motherTongueEt.getEditText().getText().toString();
                religion = religionEt.getEditText().getText().toString();
                clan = clanEt.getEditText().getText().toString();
                maritalStatus = maritalStatusAttributeAdapter.maritalStatus;
                country = countryEt.getEditText().getText().toString();
                state = stateEt.getEditText().getText().toString();
                city = cityEt.getEditText().getText().toString();
                citizenShip = citizenshipEt.getEditText().getText().toString();
                height = heightEt.getEditText().getText().toString();
                education = educationEt.getEditText().getText().toString();
                employedIn = employedInEt.getEditText().getText().toString();
                occupation = occupationEt.getEditText().getText().toString();
                salary = salaryEt.getEditText().getText().toString();
                physicalStatus = physicalStatusAdapter.physicalStatus;
                religiousValue = religiousValueEt.getEditText().getText().toString();
                ethnicity = ethnicityEt.getEditText().getText().toString();
                aboutMe = aboutMeEt.getEditText().getText().toString();


                addUsers(profileCreator, gender, name, dob, email, password, confirmPass, phone, motherTongue,
                        religion, clan, maritalStatus, country, state, city, citizenShip, height, education,
                        employedIn, occupation, salary, physicalStatus, religiousValue, ethnicity, aboutMe, currentDate, activatedstatus);


            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dobEt.getEditText().setText(dateFormat.format(myCalendar.getTime()));


    }

    private void addUsers(String profileCreator, String gender, String name, String dob, String email,
                          String password, String confirmPass, String phone, String motherTongue, String religion,
                          String clan, String maritalStatus, String country, String state, String city,
                          String citizenShip, String height, String education, String employedIn, String occupation,
                          String salary, String physicalStatus, String religiousValue, String ethnicity,
                          String aboutMe, String currentDate, String activatedstatus) {

        pd.show();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //String userId = firebaseFirestore.collection("users").document().getId();
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("userId", mAuth.getCurrentUser().getUid());
                    userMap.put("profileCreator", profileCreator);
                    userMap.put("gender", gender);
                    userMap.put("name", name);
                    userMap.put("dob", dob);
                    userMap.put("email", email);
                    userMap.put("password", password);
                    userMap.put("phone", phone);
                    userMap.put("motherTongue", motherTongue);
                    userMap.put("religion", religion);
                    userMap.put("clan", clan);
                    userMap.put("maritalStatus", maritalStatus);
                    userMap.put("country", country);
                    userMap.put("state", state);
                    userMap.put("city", city);
                    userMap.put("citizenShip", citizenShip);
                    userMap.put("height", height);
                    userMap.put("education", education);
                    userMap.put("employedIn", employedIn);
                    userMap.put("occupation", occupation);
                    userMap.put("salary", salary);
                    userMap.put("physicalStatus", physicalStatus);
                    userMap.put("religiousValue", religiousValue);
                    userMap.put("ethnicity", ethnicity);
                    userMap.put("aboutMe", aboutMe);
                    userMap.put("dateOfRegistration", currentDate);
                    userMap.put("activatedstatus", activatedstatus);

                    firebaseFirestore.collection("users")
                            .document(mAuth.getCurrentUser().getUid())
                            .set(userMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        //progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
                                        //Intent to home or previous activity
                                        //Intent previousActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                                        //startActivity(previousActivity);
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("y", "onFailure: ");

                        }
                    });


                    verificationEmail();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

/*        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                firebaseFirestore.collection("users").document(userId)
                        .set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "sent....", Toast.LENGTH_SHORT).show();
                        verificationEmail();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pd.dismiss();
                Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });*/
    }


    private void verificationEmail() {

        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Re-enable button
                        //findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            /*Name = edtName.getText().toString();
                            Email = edtEmail.getText().toString();
                            PhoneNumber = etPhoneNumber.getText().toString();
                            addData(Name, Email, PhoneNumber);*/
                            Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(home);
                            finish();

                        } else {
                            // Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;

    }

}

