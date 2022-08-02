package com.example.phils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapterClass extends RecyclerView.Adapter<UserAdapterClass.MyViewHolder> {
    List<ResponseModelUser> data;

    public UserAdapterClass(List<ResponseModelUser> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user_activity,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.sn.setText(data.get(position).getUser_id());
        holder.emp_name.setText(data.get(position).getUser_name());
        holder.post.setText(data.get(position).getEmp_type_name());
        holder.username.setText(data.get(position).getUser_name());
        holder.number.setText(data.get(position).getUser_phone_number());
        holder.otp.setText(data.get(position).getUser_otp());
        holder.worker.setText(data.get(position).getEmployee_type());
        holder.status.setText(data.get(position).getUser_status());
        holder.id.setText(data.get(position).getUser_id());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView sn,emp_name,post,username,number,otp,worker,status,id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            emp_name = itemView.findViewById(R.id.emp_name);
            post = itemView.findViewById(R.id.post);
            username = itemView.findViewById(R.id.username);
            number = itemView.findViewById(R.id.number);
            otp = itemView.findViewById(R.id.otp);
            worker = itemView.findViewById(R.id.worker);
            status = itemView.findViewById(R.id.status);
            id = itemView.findViewById(R.id.id);
        }
    }
}
