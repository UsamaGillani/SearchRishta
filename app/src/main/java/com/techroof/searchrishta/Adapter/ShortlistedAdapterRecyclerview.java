package com.techroof.searchrishta.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techroof.searchrishta.R;
import com.techroof.searchrishta.Shortlisted;

import java.util.ArrayList;
import java.util.List;

public class ShortlistedAdapterRecyclerview extends RecyclerView.Adapter<ShortlistedAdapterRecyclerview.ShortlistHolder> {


    private List<Shortlisted> shortlisteds=new ArrayList<>();
    @NonNull
    @Override
    public ShortlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_recyclerview_dashboard,parent,false);

        return new ShortlistHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortlistHolder holder, int position) {

        Shortlisted listed=shortlisteds.get(position);
        holder.tvid.setText(listed.getUserid());
        holder.tvState.setText(listed.getProvince());
        holder.tvHeight.setText(listed.getHeight());
        holder.tvCountry.setText(listed.getCountry());
        holder.tvCity.setText(listed.getCity());
        holder.tvdob.setText(listed.getDOB());
        holder.tvrelegion.setText(listed.getRelegion());
        holder.tvStatus.setText(listed.getMaritalstatus());
        holder.tvEducation.setText(listed.getEducation());
        holder.tvname.setText(listed.getName());
    }


    @Override
    public int getItemCount() {

        return shortlisteds.size();
    }

    public void setShortlisted(List<Shortlisted> shortlisted){
        this.shortlisteds=shortlisted;
        notifyDataSetChanged();
    }

    class ShortlistHolder extends RecyclerView.ViewHolder{

        TextView tvid,tvname,tvdob,tvrelegion,tvState,tvEducation,tvCity,tvStatus,
                tvCountry,tvHeight;
        CardView cardViewvieww;
        public ShortlistHolder(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tv_id);
            tvname = itemView.findViewById(R.id.tv_name);
            tvdob=itemView.findViewById(R.id.tv_dob);
            tvrelegion=itemView.findViewById(R.id.tv_crd_relegion);
            tvEducation=itemView.findViewById(R.id.tv_crd_education);
            tvStatus=itemView.findViewById(R.id.tv_crd_working_status);
            tvCity=itemView.findViewById(R.id.tv_city);
            tvCountry=itemView.findViewById(R.id.tv_nationality);
            tvState=itemView.findViewById(R.id.tv_state);
            tvHeight=itemView.findViewById(R.id.tv_crd_height);
            cardViewvieww=itemView.findViewById(R.id.parentlayout_dashboard);
        }
    }
}
