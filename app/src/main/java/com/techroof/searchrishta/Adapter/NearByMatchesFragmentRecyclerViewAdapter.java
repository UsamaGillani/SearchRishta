package com.techroof.searchrishta.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techroof.searchrishta.Interfaces.DasboardClickListener;
import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.R;
import com.techroof.searchrishta.ViewModel.ShortlistedViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class NearByMatchesFragmentRecyclerViewAdapter extends RecyclerView.Adapter<NearByMatchesFragmentRecyclerViewAdapter.ViewAdapter> {
    private static final String TAG = "RecyclerViewAdapter";
    private ShortlistedViewModel getViewmodel;
    public DasboardClickListener mlistener;


    private ArrayList<Users> UserlistData,arraylist;
    private Context context;

    private FirebaseFirestore db;
    private double latitude, longitude,startValue,endValue;
    private String distance;

    private ArrayList<String> distanceCollections;

    Users users =new Users();

    public NearByMatchesFragmentRecyclerViewAdapter(ArrayList<Users> UserlistData, Context context, DasboardClickListener listener,double latitude, double longitude,double startValue,double endValue) {
        this.UserlistData = UserlistData;
        this.context = context;
        this.mlistener = listener;
        this.arraylist=UserlistData;

        this.latitude = latitude;
        this.longitude = longitude;

        this.startValue=startValue;
        this.endValue=endValue;



        //getCurrentLocation();
        db = FirebaseFirestore.getInstance();

        distanceCollections=new ArrayList<>();

        Toast.makeText(context.getApplicationContext(), ""+UserlistData, Toast.LENGTH_SHORT).show();


    }

    @NonNull
    @Override
    public NearByMatchesFragmentRecyclerViewAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: 1");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_nearbyme, parent, false);

        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NearByMatchesFragmentRecyclerViewAdapter.ViewAdapter holder,  int position) {



        Users ld = UserlistData.get(position);
        /*      String imgProfile=ld.getImg();
        Glide.with(context)
                .load(imgProfile) // image url
                .placeholder(R.drawable.image) // any placeholder to load at start
                .override(200, 200) // resizing
                .centerCrop()
                .into(holder.prflimage);*/
        //notifyDataSetChanged();
        Log.d(TAG, "onBindViewHolder: called");



        db.collection("users")
                .document(UserlistData.get(position).getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        double lat, lng;
                        lat = Double.parseDouble(documentSnapshot.getString("Latitude"));
                        lng = Double.parseDouble(documentSnapshot.getString("Longitude"));


                        ///////////----- Calculate Distance ------///////////
                        double R = 6371.0; // km
                        double dLat = (latitude - lat) * Math.PI / 180.0;
                        double dLon = (longitude - lng) * Math.PI / 180.0;
                        double lati = lat * Math.PI / 180.0;
                        double latii = latitude * Math.PI / 180.0;


                        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) +
                                Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0) * Math.cos(lati) * Math.cos(latii);
                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                        double d = R * c;


                        DecimalFormat disFormat = new DecimalFormat("#.#");
                        distance = "" + disFormat.format(d);

                        ld.setLocation(distance);
                        //    holder.distanceText.setText(disFormat.format(d) + " Km away");
                        // holder.textViewdistance.setText(distance.toString() + " Km away");




                        if (Double.parseDouble(distance)>=startValue&&Double.parseDouble(distance)<=endValue){

                            holder.itemView.setVisibility(View.GONE);


                        }else{
                            holder.textViewid.setText(ld.getUserId());
                            holder.textViewState.setText(ld.getState());
                            holder.textviewHeight.setText(ld.getHeight());
                            holder.textViewCountry.setText(ld.getCountry());
                            holder.textViewCity.setText(ld.getCity());
                            holder.textViewdob.setText(ld.getDob());
                            holder.textViewrelegion.setText(ld.getReligion());
                            holder.textViewStatus.setText(ld.getMaritalStatus());
                            holder.textViewEducation.setText(ld.getEducation());
                            holder.textViewname.setText(ld.getName());
                            holder.textViewdistance.setText(ld.getLocation()+ " km away" );
                            String imgProfile=ld.getImg();
                            //notifyDataSetChanged();
                            Glide.with(context).load(imgProfile).into(holder.prflimage);





                        }






                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




        // imageview object


        // Toast.makeText(context.getApplicationContext(), ""+distance, Toast.LENGTH_SHORT).show();

        //locations
        /*String imgProfile=ld.getImg();
        Glide.with(context)
                .load(imgProfile).placeholder(R.drawable.male_avatar) // image url// any placeholder to load at start
                .override(200, 200) // resizing
                .centerCrop()
                .into(holder.prflimage);*/








       /* Collections.sort(distanceCollections);
        holder.textViewdistance.setText(distanceCollections.get(position));*/
        //Toast.makeText(context.getApplicationContext(), ""+distance, Toast.LENGTH_SHORT).show();




        holder.imgShortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId=ld.getUserId();
                String name=ld.getName();
                String dob=ld.getDob();
                String height=ld.getHeight();
                String relegion=ld.getReligion();
                String education=ld.getEducation();
                String maritalstatus=ld.getMaritalStatus();
                String city=ld.getCity();
                String province=ld.getState();
                String country=ld.getCountry();


                holder.imgShortlisted.setImageDrawable(context.getResources().getDrawable(R.drawable.shortlist));

                mlistener.onItemclickk(userId,name,dob,height,relegion,education,maritalstatus,city,province,country);

                //users locations




            }
        });


        holder.cardViewview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context.getApplicationContext(), "position"+ld.getImage(), Toast.LENGTH_SHORT).show();
                /*Snackbar snackbar = Snackbar
                        .make(Ho, "www.journaldev.com", Snackbar.LENGTH_LONG);
                snackbar.show();*/
               /* String movecategory=ld.getCategory();
                Log.d(TAG, "onClick: "+ld.getName());
                Intent intent= new Intent(context.getApplicationContext(), InvestmentDetailsActivity.class);
                intent.putExtra("content", movecategory);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/


            }
        });


    }

    @Override
    public int getItemCount() {
        return UserlistData.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        TextView textViewid,textViewname,textViewdob,textViewrelegion,textViewState,textViewEducation,textViewCity,textViewStatus,
        textViewCountry,textviewHeight,textViewdistance;
        CardView cardViewview;
        ImageView imgShortlisted,imgChat,imgSendInterest;
        CircleImageView prflimage;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            textViewid = itemView.findViewById(R.id.tv_id);
            textViewname = itemView.findViewById(R.id.tv_name);
            textViewdob=itemView.findViewById(R.id.tv_dob);
            textViewrelegion=itemView.findViewById(R.id.tv_crd_relegion);
            textViewEducation=itemView.findViewById(R.id.tv_crd_education);
            textViewStatus=itemView.findViewById(R.id.tv_crd_working_status);
            textViewCity=itemView.findViewById(R.id.tv_city);
            textViewCountry=itemView.findViewById(R.id.tv_nationality);
            textViewState=itemView.findViewById(R.id.tv_state);
            textviewHeight=itemView.findViewById(R.id.tv_crd_height);
            cardViewview=itemView.findViewById(R.id.parentlayout_dashboard);
            imgShortlisted=itemView.findViewById(R.id.img_shortlisted);
            imgChat=itemView.findViewById(R.id.img_chat);
            imgSendInterest=itemView.findViewById(R.id.img_send_interest);
            prflimage=itemView.findViewById(R.id.img_icons);
            textViewdistance=itemView.findViewById(R.id.tv_distance);

        }
    }

}
