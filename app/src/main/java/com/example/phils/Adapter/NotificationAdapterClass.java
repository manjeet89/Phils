package com.example.phils.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelNotification;

import java.util.List;

public class NotificationAdapterClass extends RecyclerView.Adapter<NotificationAdapterClass.MyViewHolder> {

    private NotificationAdapterClass.RecycleViewClickListener listener;
    private List<ResponseModelNotification> data;

    public NotificationAdapterClass(RecycleViewClickListener listener, List<ResponseModelNotification> data) {
        this.listener = listener;
        this.data = data;
    }
    //    private Context context;
//    private List<ResponseModelNotification> data;
//
//    public NotificationAdapterClass(Context context, List<ResponseModelNotification> data) {
//        this.context = context;
//        this.data = data;
//    }


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
        if(data.get(position).getNoti_status().equals("Unread"))
            holder.noti_status.setTextColor(Color.parseColor("#ed0e1d"));
        else
            holder.noti_status.setTextColor(Color.parseColor("#0eed3e"));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sn_job,noti_message,noti_status;
        Button id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn_job = itemView.findViewById(R.id.sn_job);
            noti_message = itemView.findViewById(R.id.noti_message);
            noti_status = itemView.findViewById(R.id.noti_status);
            id = itemView.findViewById(R.id.id);
            id.setOnClickListener(this);
            id.setTextColor(Color.parseColor("#1ca6eb"));

        }

        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());

        }
    }
    public interface RecycleViewClickListener{
        void onClick(View v,int position);
    }
}
