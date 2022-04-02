package com.techroof.searchrishta.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techroof.searchrishta.Model.Users;
import com.techroof.searchrishta.R;

import java.util.ArrayList;

public class JustJoinedFragmentRecyclerViewAdapter extends RecyclerView.Adapter<JustJoinedFragmentRecyclerViewAdapter.ViewAdapter> {
    private static final String TAG = "RecyclerViewAdapter";


    private ArrayList<Users> UserlistData;
    private Context context;

    public JustJoinedFragmentRecyclerViewAdapter(ArrayList<Users> UserlistData, Context context) {
        this.UserlistData = UserlistData;
        this.context = context;
    }

    @NonNull
    @Override
    public JustJoinedFragmentRecyclerViewAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: 1");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_dashboard, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JustJoinedFragmentRecyclerViewAdapter.ViewAdapter holder, int position) {


        Log.d(TAG, "onBindViewHolder: called");
        Users ld = UserlistData.get(position);
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
        //Glide.with(context).load(ld.getImage()).into(holder.imageView);
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
       private TextView textViewid,textViewname,textViewdob,textViewrelegion,textViewState,textViewEducation,textViewCity,textViewStatus,
        textViewCountry,textviewHeight;
       private CardView cardViewview;

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

        }
    }

}
