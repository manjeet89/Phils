package com.example.phils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelJobList;

import java.util.List;

public class JobListAdapterClass extends RecyclerView.Adapter<JobListAdapterClass.MyViewHolder> {

    private Context context;
    List<ResponseModelJobList> data;

    public JobListAdapterClass(Context context, List<ResponseModelJobList> data) {
        this.context = context;
        this.data = data;
    }
    public void setFilteredList(List<ResponseModelJobList> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_job_list,parent,false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sn.setText(data.get(position).getSn());
        holder.jobclint.setText(data.get(position).getJob_name());
        holder.jobnumber.setText(data.get(position).getJob_number());
        holder.projectmanager.setText(data.get(position).getJob_manager_id());
        holder.statuslist.setText(data.get(position).getJob_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView sn,jobclint,jobnumber,projectmanager,statuslist;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.snlist);
            jobclint = itemView.findViewById(R.id.jobclintlist);
            jobnumber = itemView.findViewById(R.id.jobnumberlist);
            projectmanager = itemView.findViewById(R.id.projectmanagerlist);
            statuslist = itemView.findViewById(R.id.statuslist);
        }
    }
}
