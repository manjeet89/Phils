package com.example.phils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelNotification;

import java.util.List;

public class NotificationAdapterClass extends RecyclerView.Adapter<NotificationAdapterClass.MyViewHolder> {

    private Context context;
    private List<ResponseModelNotification> data;

    public NotificationAdapterClass(Context context, List<ResponseModelNotification> data) {
        this.context = context;
        this.data = data;
    }


    public void setFilteredList(List<ResponseModelNotification> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification,parent,false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sn_job.setText(data.get(position).getNoti_id());
        holder.noti_message.setText(data.get(position).getNoti_message());
        holder.noti_status.setText(data.get(position).getNoti_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sn_job,noti_message,noti_status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn_job = itemView.findViewById(R.id.sn_job);
            noti_message = itemView.findViewById(R.id.noti_message);
            noti_status = itemView.findViewById(R.id.noti_status);
        }
    }
}
