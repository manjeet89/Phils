package com.example.phils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelJobSize;

import java.util.List;

public class JobSizeAdapterClass extends RecyclerView.Adapter<JobSizeAdapterClass.MyViewHolder> {
    private Context context;
    private List<ResponseModelJobSize> data;

    public JobSizeAdapterClass(Context context, List<ResponseModelJobSize> data) {
        this.context = context;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelJobSize> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_job_size_activity,parent,false);
        return new MyViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sn.setText(data.get(position).getJob_size_id());
        holder.category.setText(data.get(position).getJob_size_name());
        holder.status.setText(data.get(position).getJob_size_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sn,category,cat_group,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn_job);
            category = itemView.findViewById(R.id.category_job);
            status = itemView.findViewById(R.id.status_job);
        }
    }
}
