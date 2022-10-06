package com.example.phils.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelJobSize;

import java.util.List;

public class JobSizeAdapterClass extends RecyclerView.Adapter<JobSizeAdapterClass.MyViewHolder> {
    private JobSizeAdapterClass.RecycleViewClickListener listener;
    private List<ResponseModelJobSize> data;

    public JobSizeAdapterClass(RecycleViewClickListener listener, List<ResponseModelJobSize> data) {
        this.listener = listener;
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
        holder.sn.setText(data.get(position).getSn());
        holder.category.setText(data.get(position).getJob_size_name());
        holder.status.setText(data.get(position).getJob_size_status());
        if(data.get(position).getJob_size_status().equals("Disable"))
            holder.status.setTextColor(Color.parseColor("#ed0e1d"));
        else
            holder.status.setTextColor(Color.parseColor("#0eed3e"));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sn,category,cat_group,status;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn_job);
            category = itemView.findViewById(R.id.category_job);
            status = itemView.findViewById(R.id.status_job);
            btn = itemView.findViewById(R.id.id);
            btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());

        }
    }

    public interface RecycleViewClickListener{
        void onClick(View v,int position);
    }
}